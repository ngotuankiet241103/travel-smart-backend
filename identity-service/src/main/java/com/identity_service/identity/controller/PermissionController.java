package com.identity_service.identity.controller;

import com.identity_service.identity.dto.request.PermissionRequest;
import com.identity_service.identity.dto.response.ApiResponse;
import com.identity_service.identity.dto.response.PermissionResponse;
import com.identity_service.identity.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new permission",description = "Returns single permission")
    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest permissionRequest){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(permissionRequest))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all permissions",description = "Returns all permission")
    @GetMapping
    public ApiResponse<List<PermissionResponse>> getByRoleName(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }
}
