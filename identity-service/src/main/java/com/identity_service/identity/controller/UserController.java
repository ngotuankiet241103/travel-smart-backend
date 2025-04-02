package com.identity_service.identity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.identity_service.identity.dto.request.PermissionRequest;
import com.identity_service.identity.dto.request.UserBlockRequest;
import com.identity_service.identity.dto.request.UserUpdatePermission;
import com.identity_service.identity.dto.response.ApiResponse;
import com.identity_service.identity.dto.response.PageableResponse;
import com.identity_service.identity.dto.response.ReportResponse;
import com.identity_service.identity.dto.response.UserResponse;
import com.identity_service.identity.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Hidden
    @GetMapping("/internal/{email}/email")
    public ApiResponse<String> getUserToShare(@PathVariable("email") String email){
        return ApiResponse.<String>builder()
                .result(userService.getUserToShare(email))
                .build();
    }
    @Operation(summary = "Get all users",description = "Returns all users")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<PageableResponse<List<UserResponse>>> getAll(@RequestParam(value = "page",defaultValue = "1") int page,
                                                  @RequestParam(value = "limit",defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        return ApiResponse.<PageableResponse<List<UserResponse>>>builder()
                .result(userService.findAll(pageable))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/permissions/{user-id}/user")
    public ApiResponse<UserResponse> updatePermission(@PathVariable("user-id") String userId,
                                                      @RequestBody UserUpdatePermission updatePermission){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateRole(userId,updatePermission))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/block/{user-id}")
    public ApiResponse<UserResponse> blockUser(@PathVariable("user-id") String userId,
                                               @RequestBody UserBlockRequest userBlockRequest){
        return ApiResponse.<UserResponse>builder()
                .result(userService.blockUser(userId,userBlockRequest))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/report")
    public ApiResponse<ReportResponse> getReportUser(@RequestParam("from") Date from, @RequestParam("to") Date to){
        return  ApiResponse.<ReportResponse>builder()
                .result(userService.getReport(from,to))
                .build();
    }
    @GetMapping("/token")
    public ApiResponse<String> getToken() throws JsonProcessingException {
        return  ApiResponse.<String>builder()
                .result(userService.getToken())
                .build();
    }
    @GetMapping("/statistics")
    public ApiResponse<Map<String,Object>> getStatistic(){
        return ApiResponse.<Map<String,Object>>builder()
                .result(userService.getStatistic())
                .build();
    }

}
