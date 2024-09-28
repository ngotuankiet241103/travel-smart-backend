package com.travelsmart.profile_service.mapper;

import com.travelsmart.profile_service.dto.response.AvatarResponse;
import com.travelsmart.profile_service.entity.AvatarEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvatarMapper {
    AvatarResponse toAvatarResponse(AvatarEntity avatarEntity);
}
