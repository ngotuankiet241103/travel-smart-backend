package com.travelsmart.profile_service.mapper;

import com.travelsmart.event.dto.ProfileRequest;
import com.travelsmart.profile_service.dto.request.ProfileUpdateRequest;
import com.travelsmart.profile_service.dto.response.ProfileResponse;
import com.travelsmart.profile_service.entity.ProfileEntity;
import com.travelsmart.saga.profile.command.ProfileCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileEntity toProfileEntity(ProfileRequest profileRequest);
    ProfileEntity toProfileEntity(ProfileCommand profileCommand);
    ProfileEntity toProfileEntity(ProfileUpdateRequest profileUpdateRequest);
    ProfileResponse toProfileResponse(ProfileEntity profileEntity);
}
