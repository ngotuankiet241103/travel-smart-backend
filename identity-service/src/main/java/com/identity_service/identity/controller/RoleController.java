package com.identity_service.identity.controller;

import com.identity_service.identity.dto.request.RoleRequest;
import com.identity_service.identity.dto.response.ApiResponse;
import com.identity_service.identity.dto.response.RoleResponse;
import com.identity_service.identity.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new role",description = "Returns single role")
    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest roleRequest){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(roleRequest))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all roles",description = "Returns all roles")
    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.findAll())
                .build();
    }
}
