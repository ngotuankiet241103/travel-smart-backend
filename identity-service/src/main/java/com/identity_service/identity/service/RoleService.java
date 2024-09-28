package com.identity_service.identity.service;

import com.identity_service.identity.dto.request.RoleRequest;
import com.identity_service.identity.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest roleRequest);

    List<RoleResponse> findAll();
}
