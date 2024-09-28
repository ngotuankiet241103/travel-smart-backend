package com.identity_service.identity.mapper;

import com.identity_service.identity.dto.request.RegisterRequest;
import com.identity_service.identity.dto.response.UserResponse;
import com.identity_service.identity.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(RegisterRequest registerRequest);
    UserResponse toUserResponse(UserEntity userEntity);
}
