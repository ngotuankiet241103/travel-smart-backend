package com.travelsmart.profile_service.service;

import com.travelsmart.event.dto.ProfileRequest;
import com.travelsmart.profile_service.dto.request.AvatarRequest;
import com.travelsmart.profile_service.dto.request.HobbyRequest;
import com.travelsmart.profile_service.dto.request.ProfileUpdateAvatar;
import com.travelsmart.profile_service.dto.request.ProfileUpdateRequest;
import com.travelsmart.profile_service.dto.response.AvatarResponse;
import com.travelsmart.profile_service.dto.response.ProfileInternalResponse;
import com.travelsmart.profile_service.dto.response.ProfileResponse;
import com.travelsmart.saga.profile.command.ProfileCommand;

public interface ProfileService {
    void create(ProfileCommand profileCommand);

    ProfileResponse update(String id, ProfileUpdateRequest profileUpdateRequest);

    ProfileResponse findById(String userId);

    String getUserToShare(String email);

    AvatarResponse uploadAvatar(AvatarRequest avatarRequest);

    void deleteImage(Long id);

    ProfileResponse changeAvatar(ProfileUpdateAvatar profileUpdateAvatar);

    ProfileInternalResponse findInternalById(String userId);

    ProfileResponse updateHobbies(HobbyRequest hobbyRequest);
}
