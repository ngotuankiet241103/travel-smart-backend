package com.identity_service.identity.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.identity_service.identity.dto.request.ChangePasswordRequest;
import com.identity_service.identity.dto.request.RegisterRequest;
import com.identity_service.identity.dto.request.UserBlockRequest;
import com.identity_service.identity.dto.request.UserUpdatePermission;
import com.identity_service.identity.dto.response.PageableResponse;
import com.identity_service.identity.dto.response.Paging;
import com.identity_service.identity.dto.response.ReportResponse;
import com.identity_service.identity.dto.response.UserResponse;
import com.identity_service.identity.entity.RoleEntity;
import com.identity_service.identity.entity.UserEntity;
import com.identity_service.identity.exception.CustomRuntimeException;
import com.identity_service.identity.exception.ErrorCode;
import com.identity_service.identity.mapper.UserMapper;
import com.identity_service.identity.repository.RoleRepository;
import com.identity_service.identity.repository.UserRepository;
import com.identity_service.identity.service.TokenRedisService;
import com.identity_service.identity.service.UserService;
import com.identity_service.identity.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRedisService tokenRedisService;
    @Override
    public boolean isExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserResponse createUser(RegisterRequest request) {
        UserEntity user = userMapper.toUserEntity(request);
//        RoleEntity role = roleRepository.findByName("USER")
//                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.ROLE_NOT_FOUND));
//        user.setRoles(new HashSet<>(List.of(role)));
        return  mappingOne(userRepository.save(user));
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findById(authentication.getName())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));
        boolean isMatch = passwordEncoder.matches(
                changePasswordRequest.getOldPassword(),user.getPassword());
        if(!isMatch) throw new CustomRuntimeException(ErrorCode.PASSWORD_NOT_MATCH);
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return "Password is changed success";
    }

    @Override
    public String getUserToShare(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElse(null);
        return user != null ? user.getId() : null;
    }

    @Override
    public PageableResponse<List<UserResponse>> findAll(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);
        Paging paging = Paging.builder()
                .limit(pageable.getPageNumber())
                .page(page.getNumber())
                .totalPages(page.getTotalPages())
                .build();
        PageableResponse<List<UserResponse>> response = PageableResponse.
                <List<UserResponse>>builder()
                .data(mappingList(page.getContent()))
                .paging(paging)
                .build();
        return response;
    }

    @Override
    public UserResponse updateRole(String userId, UserUpdatePermission role) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));

        List<RoleEntity> roles = role.getNames().stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new CustomRuntimeException(ErrorCode.ROLE_NOT_FOUND)))
                .toList();

        user.setRoles(new HashSet<>(roles));
        return mappingOne(userRepository.save(user));
    }

    @Override
    public UserResponse blockUser(String userId, UserBlockRequest userBlockRequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));
        System.out.println(userBlockRequest.isBlock());
        user.setBlock(userBlockRequest.isBlock());
        return mappingOne(userRepository.save(user));
    }

    @Override
    public ReportResponse getReport(Date from, Date to) {
        LocalDate fromLocalDate = DateUtils.convertToLocalDate(from);
        LocalDate toLocalDate = DateUtils.convertToLocalDate(to);
        long total = DateUtils.calculateDays(fromLocalDate,toLocalDate);
        if(total == 7 ){

        }
        return null;
    }

    @Override
    public String getToken() throws JsonProcessingException {
        return tokenRedisService.getToken();
    }

    @Override
    public Map<String, Object> getStatistic() {
        Map<String,Object> response = new HashMap<>();
        long total = userRepository.count();
        response.put("total", total);
        response.put("label","user");
        return response;
    }

    private List<UserResponse> mappingList(List<UserEntity> e){
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }
    private UserResponse mappingOne(UserEntity user) {
        return userMapper.toUserResponse(user);
    }
}
