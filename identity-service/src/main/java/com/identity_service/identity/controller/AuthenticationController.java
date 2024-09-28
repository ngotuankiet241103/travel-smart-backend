package com.identity_service.identity.controller;

import com.identity_service.event.dto.EmailRequest;
import com.identity_service.identity.dto.request.*;
import com.identity_service.identity.dto.response.ApiResponse;
import com.identity_service.identity.dto.response.IntrospectResponse;
import com.identity_service.identity.dto.response.TokenResponse;
import com.identity_service.identity.exception.CustomRuntimeException;
import com.identity_service.identity.exception.ErrorCode;
import com.identity_service.identity.service.AuthenticationService;
import com.identity_service.identity.service.ConfirmTokenService;
import com.identity_service.identity.service.UserService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ConfirmTokenService confirmTokenService;

    @Operation(summary = "Confirm token for when user confirm account in gmail")
    @GetMapping("/confirm-token")
    public void confirmToken(@RequestParam("token") String token){
        authenticationService.verifyToken(token);
    }
    @Operation(summary = "Login user", description = "Returns token")
    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return ApiResponse.<TokenResponse>builder()
                .result(authenticationService.login(authenticationRequest))
                .build();
    }
    @Operation(summary = "Reset password", description = "Returns notification")
    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        return ApiResponse.<String>builder()
                .result(authenticationService.resetPassword(resetPasswordRequest))
                .build();
    }
    @Operation(summary = "Logout")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody RefreshRequest refreshRequest) throws ParseException, JOSEException {
        authenticationService.logout(refreshRequest);
        return ApiResponse.<Void>builder().build();
    }
    @Operation(summary = "Refresh token", description = "Returns new token")
    @PostMapping("/refresh-token")
    public ApiResponse<TokenResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) throws ParseException {
        return ApiResponse.<TokenResponse>builder()
                .result(authenticationService.refreshToken(refreshRequest))
                .build();
    }
    @Operation(summary = "Validated token", description = "Returns boolean .")
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest){
        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(introspectRequest))
                .build();
    }
    @Operation(summary = "Register account", description = "Returns a token.")
    @PostMapping("/register")
    public ApiResponse<TokenResponse> register(@RequestBody RegisterRequest registerRequest){
        boolean isExists =  userService.isExistsByEmail(registerRequest.getEmail());
        if(isExists){
            throw new CustomRuntimeException(ErrorCode.EMAIL_EXIST);
        }
        return ApiResponse.<TokenResponse>builder()
                .result(authenticationService.register(registerRequest))
                .build();
    }
    @Operation(summary = "Forgot password")
    @PostMapping("/forgot-password")
    public ApiResponse<Void> forgotPassword(@RequestBody UserForgotPassRequest forgotPassRequest){
        authenticationService.forgotPassword(forgotPassRequest);
        return ApiResponse.<Void>builder()
                .message("")
                .build();
    }
    @Operation(summary = "Change password", description = "Returns a notification.")
    @PutMapping("/change-password")
    public ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return ApiResponse.<String>builder()
                .message(userService.changePassword(changePasswordRequest))
                .build();
    }


    @KafkaListener(topics = "profileUser-success")
    public void onProfileInitSuccess(Object value){
//        System.out.println(value);

            authenticationService.verifyEmail();

    }
}
