package com.identity_service.identity.service;

import com.identity_service.identity.dto.request.ChangePasswordRequest;
import com.identity_service.identity.dto.request.RegisterRequest;
import com.identity_service.identity.dto.response.PageableResponse;
import com.identity_service.identity.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    boolean isExistsByEmail(String email);
    UserResponse createUser(RegisterRequest request);

    String changePassword(ChangePasswordRequest changePasswordRequest);

    String getUserToShare(String email);

    PageableResponse<List<UserResponse>> findAll(Pageable pageable);
}
