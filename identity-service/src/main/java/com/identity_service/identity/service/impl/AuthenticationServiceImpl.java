package com.identity_service.identity.service.impl;

import com.identity_service.event.dto.NotificationEvent;
import com.identity_service.identity.constaint.ConfirmTokenType;
import com.identity_service.identity.constaint.PredefinedRole;
import com.identity_service.identity.dto.request.*;
import com.identity_service.event.dto.ProfileRequest;
import com.identity_service.identity.dto.response.IntrospectResponse;
import com.identity_service.identity.dto.response.TokenResponse;
import com.identity_service.identity.entity.ConfirmTokenEntity;
import com.identity_service.identity.entity.RoleEntity;
import com.identity_service.identity.entity.TokenEntity;
import com.identity_service.identity.entity.UserEntity;
import com.identity_service.identity.exception.CustomRuntimeException;
import com.identity_service.identity.exception.ErrorCode;
import com.identity_service.identity.mapper.UserMapper;
import com.identity_service.identity.repository.ConfirmTokenRepository;
import com.identity_service.identity.repository.RoleRepository;
import com.identity_service.identity.repository.TokenRepository;
import com.identity_service.identity.repository.UserRepository;
import com.identity_service.identity.service.AuthenticationService;
import com.identity_service.identity.service.TokenProviderService;
import com.identity_service.identity.utils.CalendarUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.travelsmart.saga.notication.command.NotificationCommand;
import com.travelsmart.saga.profile.command.ProfileCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenProviderService tokenProviderService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final ConfirmTokenRepository confirmTokenRepository;
    private final KafkaTemplate<String,Object> template;
    private final RoleRepository roleRepository;
    private  CountDownLatch latch;
    @Override
    public TokenResponse login(AuthenticationRequest authenticationRequest) {
        UserEntity userEntity = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));
        boolean isMatch = passwordEncoder.matches(
                authenticationRequest.getPassword(),userEntity.getPassword());
        if(!isMatch) throw new CustomRuntimeException(ErrorCode.PASSWORD_NOT_MATCH);
        
        
        return buildTokenResponse(userEntity) ;
    }

    @Override
    public TokenResponse register(RegisterRequest registerRequest) {
        UserEntity userEntity = userMapper.toUserEntity(registerRequest);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        HashSet<RoleEntity> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        userEntity.setRoles(roles);

        userRepository.save(userEntity);
        try{
            ProfileCommand profileRequest = ProfileCommand.builder()
                    .userId(userEntity.getId())
                    .email(registerRequest.getEmail())
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .build();

            template.send("createUser-success", profileRequest);
            verifyEmail(registerRequest,userEntity);
            return buildTokenResponse(userEntity);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;

    }
    private void verifyEmail(RegisterRequest registerRequest,UserEntity user) throws InterruptedException {
        ConfirmTokenEntity confirmToken = buildTokenVerify(user);
        confirmTokenRepository.save(confirmToken);

        Map<String,Object> objects = new HashMap<>();
        objects.put("subject","Verify email");
        objects.put("name", registerRequest.getFirstName() + registerRequest.getLastName());
        objects.put("link", "<a href = http://localhost:8080/identity/auth/confirm-token?token="  +confirmToken.getToken() + ">Click here</a>" );
        String templateCode = "You need to verify email {name}. Please click here: {link}";
        NotificationCommand notificationEvent = NotificationCommand.builder()
                .chanel("EMAIL")
                .params(objects)
                .recipient(registerRequest.getEmail())
                .templateCode(templateCode)
                .nextEvent("profileUser-success")
                .build();
        template.send("notification",notificationEvent);
        try{

            latch = new CountDownLatch(1);
            latch.await(15,TimeUnit.MINUTES);
            System.out.println("123");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }

        confirmToken = confirmTokenRepository.findByToken(confirmToken.getToken());
        if(!confirmToken.isConfirm()){
            confirmToken.setExpired(true);
            confirmTokenRepository.save(confirmToken);
        }

    }
    private ConfirmTokenEntity buildTokenVerify(UserEntity user){
        String token = UUID.randomUUID().toString();
        ConfirmTokenEntity confirmToken = ConfirmTokenEntity.builder()
                .token(token)
                .timeExpired(CalendarUtils.plusTime(Calendar.MINUTE,15))
                .user(user)
                .type(ConfirmTokenType.VERIFY)
                .build();
        return confirmToken;
    }
    private void checkVerify(){

    }
    @Transactional
    private TokenResponse buildTokenResponse(UserEntity userEntity){
        String accessToken = tokenProviderService.buildAccessToken(userEntity);
        String refreshToken = tokenProviderService.buildRefreshToken(userEntity);
        saveToken(refreshToken,userEntity);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Async
    private void saveToken(String token,UserEntity userEntity)  {
        try{

            SignedJWT signedJWT = SignedJWT.parse(token);
            System.out.println(signedJWT);


            TokenEntity tokenEntity  = TokenEntity.builder()
                    .token(signedJWT.getJWTClaimsSet().getJWTID())
                    .user(userEntity)
                    .build();
            tokenRepository.expiredAll(userEntity.getId());
            tokenRepository.save(tokenEntity);
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;

        try {
            tokenProviderService.verifyToken(token, false);
        } catch (JOSEException | ParseException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();

    }

    @Override
    public void verifyEmail() {

    }

    @Override
    public void verifyToken(String token) {
        ConfirmTokenEntity confirmToken = confirmTokenRepository.findByToken(token);
        if(confirmToken.getTimeExpired().before(new Date())){
            throw new CustomRuntimeException(ErrorCode.TOKEN_EXPRIRED);
        }
        if(confirmToken.getType() == ConfirmTokenType.VERIFY){
            UserEntity user = confirmToken.getUser();
            if(user.isEnable()) {
                throw new CustomRuntimeException(ErrorCode.EMAIL_IS_VERIFIED);
            }
            user.setEnable(true);
            userRepository.save(user);
            latch.countDown();

        }
    }

    @Override
    public void forgotPassword(UserForgotPassRequest forgotPassRequest) {
        UserEntity user = userRepository.findByEmail(forgotPassRequest.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));
        String token = UUID.randomUUID().toString();
        expiredAllToken(user);
        ConfirmTokenEntity confirmTokenEntity = ConfirmTokenEntity.builder()
                .token(token)
                .type(ConfirmTokenType.FORGOT_PASSWORD)
                .timeExpired(CalendarUtils.plusTime(Calendar.MINUTE,15))
                .user(user)
                .build();

        Map<String,Object> params = new HashMap<>();
        params.put("url",forgotPassRequest.getUrlRedirect() + "?token=" + token);
        params.put("subject",user.getEmail());
        NotificationCommand notificationCommand = NotificationCommand.builder()
                .chanel("EMAIL")
                .recipient(user.getEmail())
                .templateCode("<a href={url}>Click here</a>")
                .params(params)
                .build();
        template.send("notification",notificationCommand);
        confirmTokenRepository.save(confirmTokenEntity);
        System.out.println(token);

    }

    @Async
    private void expiredAllToken(UserEntity user){
        confirmTokenRepository.expiredAllTokenForgot(user.getId(),ConfirmTokenType.FORGOT_PASSWORD);
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        ConfirmTokenEntity confirmTokenEntity = confirmTokenRepository.
                findByTokenAndType(resetPasswordRequest.getToken(),ConfirmTokenType.FORGOT_PASSWORD)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.CONFIRMTOKEN_NOT_FOUND));
        if(confirmTokenEntity.isExpired()){
            throw new CustomRuntimeException(ErrorCode.CONFIRMTOKEN_IS_EXPIRED);
        }
        if(confirmTokenEntity.getTimeExpired().before(new Date())){
            confirmTokenEntity.setExpired(true);
            confirmTokenRepository.save(confirmTokenEntity);
            throw new CustomRuntimeException(ErrorCode.CONFIRMTOKEN_IS_EXPIRED);
        }
        UserEntity user = confirmTokenEntity.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(user);

        confirmTokenEntity.setConfirm(true);
        confirmTokenEntity.setExpired(true);
        confirmTokenRepository.save(confirmTokenEntity);
        return "Update password success";
    }

    @Override
    public void logout(RefreshRequest refreshRequest) throws ParseException, JOSEException {
        SignedJWT signedJWT = tokenProviderService.verifyToken(refreshRequest.getToken(),true);
        String jit = signedJWT.getJWTClaimsSet().getJWTID();

        TokenEntity tokenEntity = tokenRepository.findByToken(jit)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.TOKEN_NOT_FOUND));
        tokenEntity.setExpired(true);
        tokenRepository.save(tokenEntity);
    }

    @Override
    public TokenResponse refreshToken(RefreshRequest refreshRequest) throws ParseException {
        SignedJWT signedJWT = null;
        try{
             signedJWT =  tokenProviderService.verifyToken(refreshRequest.getToken(),true);
             String jit = signedJWT.getJWTClaimsSet().getJWTID();
            TokenEntity tokenEntity = tokenRepository.findByToken(jit)
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.TOKEN_NOT_FOUND));
            if(tokenEntity.isExpired()){
                throw new CustomRuntimeException(ErrorCode.UNAUTHORIZED);
            }

             UserEntity user = userRepository.findById(signedJWT.getJWTClaimsSet().getClaims().get("id").toString())
                     .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));

             return buildTokenResponse(user);
        }
        catch (Exception exception){
                tokenRepository.expiredToken(signedJWT.getJWTClaimsSet().getJWTID());

                throw new CustomRuntimeException(ErrorCode.UNAUTHORIZED);

        }

    }

}
