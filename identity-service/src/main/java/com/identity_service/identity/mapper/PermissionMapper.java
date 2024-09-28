package com.identity_service.identity.mapper;

import com.identity_service.identity.dto.request.PermissionRequest;
import com.identity_service.identity.dto.response.PermissionResponse;
import com.identity_service.identity.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(PermissionEntity permissionEntity);

    PermissionEntity toPermissionEntity(PermissionRequest permissionRequest);
}
