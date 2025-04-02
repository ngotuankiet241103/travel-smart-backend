package com.identity_service.identity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.identity_service.identity.dto.request.ChangePasswordRequest;
import com.identity_service.identity.dto.request.RegisterRequest;
import com.identity_service.identity.dto.request.UserBlockRequest;
import com.identity_service.identity.dto.request.UserUpdatePermission;
import com.identity_service.identity.dto.response.PageableResponse;
import com.identity_service.identity.dto.response.ReportResponse;
import com.identity_service.identity.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface UserService {
    boolean isExistsByEmail(String email);
    UserResponse createUser(RegisterRequest request);
    String changePassword(ChangePasswordRequest changePasswordRequest);
    String getUserToShare(String email);
    PageableResponse<List<UserResponse>> findAll(Pageable pageable);
    UserResponse updateRole(String userId, UserUpdatePermission updatePermission);
    UserResponse blockUser(String userId, UserBlockRequest userBlockRequest);

    ReportResponse getReport(Date from, Date to);

    String getToken() throws JsonProcessingException;

    Map<String, Object> getStatistic();
}
