package com.identity_service.identity.mapper;

import com.identity_service.identity.dto.request.RoleRequest;
import com.identity_service.identity.dto.response.RoleResponse;
import com.identity_service.identity.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    RoleEntity toRoleEntity(RoleRequest roleRequest);
    @Mapping(target = "permissions",ignore = true)
    RoleResponse toRoleResponse(RoleEntity roleEntity);
}
