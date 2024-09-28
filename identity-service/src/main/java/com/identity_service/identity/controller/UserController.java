package com.identity_service.identity.controller;

import com.identity_service.identity.dto.response.ApiResponse;
import com.identity_service.identity.dto.response.PageableResponse;
import com.identity_service.identity.dto.response.UserResponse;
import com.identity_service.identity.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
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

}
