package com.identity_service.identity.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UserUpdatePermission {
    private List<String> names;
}
