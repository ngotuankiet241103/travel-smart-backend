package com.identity_service.identity.service.impl;

import com.identity_service.identity.dto.request.PermissionRequest;
import com.identity_service.identity.dto.response.PermissionResponse;
import com.identity_service.identity.entity.PermissionEntity;
import com.identity_service.identity.mapper.PermissionMapper;
import com.identity_service.identity.repository.PermissionRepository;
import com.identity_service.identity.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    @Override
    public PermissionResponse create(PermissionRequest permissionRequest) {
        PermissionEntity permissionEntity = permissionMapper.toPermissionEntity(permissionRequest);
        return mappingOne(permissionRepository.save(permissionEntity));
    }

    @Override
    public List<PermissionResponse> getAll() {
        return mappingList(permissionRepository.findAll());
    }


    private PermissionResponse mappingOne(PermissionEntity permissionEntity){
        return  permissionMapper.toPermissionResponse(permissionEntity);
    }
    private List<PermissionResponse> mappingList(List<PermissionEntity> e){
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }
}
