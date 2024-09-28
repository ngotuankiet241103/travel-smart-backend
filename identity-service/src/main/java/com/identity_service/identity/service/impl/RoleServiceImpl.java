package com.identity_service.identity.service.impl;

import com.identity_service.identity.dto.request.RoleRequest;
import com.identity_service.identity.dto.response.RoleResponse;
import com.identity_service.identity.entity.PermissionEntity;
import com.identity_service.identity.entity.RoleEntity;
import com.identity_service.identity.exception.CustomRuntimeException;
import com.identity_service.identity.exception.ErrorCode;
import com.identity_service.identity.mapper.PermissionMapper;
import com.identity_service.identity.mapper.RoleMapper;
import com.identity_service.identity.repository.PermissionRepository;
import com.identity_service.identity.repository.RoleRepository;
import com.identity_service.identity.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    @Override
    public RoleResponse create(RoleRequest roleRequest) {
        RoleEntity role = roleMapper.toRoleEntity(roleRequest);
       Set<PermissionEntity> permissions = roleRequest.getPermissions().stream()
                .map(name -> permissionRepository.findByName(name)
                        .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PERSMISSION_NOT_FOUND)))
                .collect(Collectors.toSet());
       role.setPermissions(permissions);
        return mappingOne(roleRepository.save(role));
    }

    @Override
    public List<RoleResponse> findAll() {
        return mappingList(roleRepository.findAll());
    }
    private List<RoleResponse> mappingList(List<RoleEntity> e){
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }
    private RoleResponse mappingOne(RoleEntity role){
        RoleResponse roleResponse = roleMapper.toRoleResponse(role);
        List<PermissionEntity> permissionEntities  = new ArrayList<>(role.getPermissions());
        roleResponse.setPermissions(permissionEntities.stream().map(permissionEntity -> permissionMapper.toPermissionResponse(permissionEntity)).toList());
        return roleResponse;
    }
}
