package com.identity_service.identity.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class RoleRequest {
    @NotEmpty(message = "NAME_INVALID")
    private String name;
    @NotEmpty(message = "DESCRIPTION_INVALID")
    private String description;
    private List<String> permissions;
}
