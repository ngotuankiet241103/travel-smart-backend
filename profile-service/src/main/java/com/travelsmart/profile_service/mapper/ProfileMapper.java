package com.travelsmart.profile_service.mapper;

import com.travelsmart.event.dto.ProfileCommand;
import com.travelsmart.event.dto.ProfileRequest;
import com.travelsmart.profile_service.dto.request.ProfileUpdateRequest;
import com.travelsmart.profile_service.dto.response.ProfileResponse;
import com.travelsmart.profile_service.entity.ProfileEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(target = "email",ignore = true)
    @Mapping(target = "hobbies",ignore = true)
    ProfileEntity toProfileEntity(ProfileRequest profileRequest);
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "hobbies",ignore = true)
    ProfileEntity toProfileEntity(ProfileCommand profileCommand);
    @Mapping(target = "hobbies",ignore = true)
    @Mapping(target = "email",ignore = true)
    @Mapping(target = "id",ignore = true)
    ProfileEntity toProfileEntity(ProfileUpdateRequest profileUpdateRequest);
    @Mapping(target = "hobbies",ignore = true)
    @Mapping(target = "avatar",ignore = true)
    ProfileResponse toProfileResponse(ProfileEntity profileEntity);
}
