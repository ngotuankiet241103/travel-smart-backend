package com.identity_service.identity.service;

import com.identity_service.identity.dto.request.PermissionRequest;
import com.identity_service.identity.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest permissionRequest);



    List<PermissionResponse> getAll();
}
