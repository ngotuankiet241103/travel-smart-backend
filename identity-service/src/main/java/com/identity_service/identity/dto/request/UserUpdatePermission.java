package com.identity_service.identity.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdatePermission {
    @NotNull(message = "NAMES_INVALID")
    private List<String> names;
}
