package com.identity_service.identity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PermissionRequest {
    private String name;
    private String description;
}
