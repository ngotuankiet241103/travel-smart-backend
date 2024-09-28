package com.identity_service.identity.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleRequest {
    private String name;
    private String description;
    private List<String> permissions;
}
