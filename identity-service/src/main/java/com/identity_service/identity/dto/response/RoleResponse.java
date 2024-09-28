package com.identity_service.identity.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class RoleResponse {
    private String name;
    private String description;
    private List<PermissionResponse> permissions;
}
