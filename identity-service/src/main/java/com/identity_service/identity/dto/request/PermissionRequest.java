package com.identity_service.identity.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PermissionRequest {
    @NotEmpty(message = "NAME_INVALID")
    private String name;
    @NotEmpty(message = "DESCRIPTION_INVALID")
    private String description;
}
