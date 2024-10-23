package com.travelsmart.profile_service.service.impl;

import com.travelsmart.event.dto.ProfileRequest;
import com.travelsmart.profile_service.dto.request.AvatarRequest;
import com.travelsmart.profile_service.dto.request.ProfileUpdateAvatar;
import com.travelsmart.profile_service.dto.request.ProfileUpdateRequest;
import com.travelsmart.profile_service.dto.response.AvatarResponse;
import com.travelsmart.profile_service.dto.response.MediaHttpResponse;
import com.travelsmart.profile_service.dto.response.ProfileInternalResponse;
import com.travelsmart.profile_service.dto.response.ProfileResponse;
import com.travelsmart.profile_service.entity.AvatarEntity;
import com.travelsmart.profile_service.entity.ProfileEntity;
import com.travelsmart.profile_service.exception.CustomRuntimeException;
import com.travelsmart.profile_service.exception.ErrorCode;
import com.travelsmart.profile_service.mapper.AvatarMapper;
import com.travelsmart.profile_service.mapper.ProfileMapper;
import com.travelsmart.profile_service.repository.AvatarRepository;
import com.travelsmart.profile_service.repository.ProfileRepository;
import com.travelsmart.profile_service.repository.httpclient.MediaClient;
import com.travelsmart.profile_service.service.ProfileService;
import com.travelsmart.saga.profile.command.ProfileCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final KafkaTemplate<String,Object> template;
    private final MediaClient mediaClient;
    private final AvatarRepository avatarRepository;
    private final AvatarMapper avatarMapper;

    @Override
    public void create(ProfileCommand profileCommand) {
        ProfileEntity profileEntity = profileMapper.toProfileEntity(profileCommand);
        profileEntity.setId(profileCommand.userId());
        profileRepository.save(profileEntity);
    }

    @Override
    public ProfileResponse update(String id, ProfileUpdateRequest profileUpdateRequest) {
        boolean isExist = profileRepository.existsById(id);
        if(!isExist) throw new CustomRuntimeException(ErrorCode.USER_NOT_FOUND);
        ProfileEntity profileEntity = profileMapper.toProfileEntity(profileUpdateRequest);
        profileEntity.setId(id);
        profileEntity.setFirstName(profileUpdateRequest.getFirstName());
        profileEntity.setLastName(profileUpdateRequest.getLastName());
        return mappingOne(profileRepository.save(profileEntity));
    }

    @Override
    public ProfileResponse findById(String userId) {
        return mappingOne(profileRepository.findById(userId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public String getUserToShare(String email) {
        ProfileEntity profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        return profile.getId();
    }

    @Override
    public AvatarResponse uploadAvatar(AvatarRequest avatarRequest) {
        MediaHttpResponse mediaHttpResponse = mediaClient.uploadFile(avatarRequest.getFile()).getResult();
        AvatarEntity avatarEntity = AvatarEntity.builder()
                .id(mediaHttpResponse.getId())
                .url(mediaHttpResponse.getUrl())
                .build();

        return avatarMapper.toAvatarResponse(avatarRepository.save(avatarEntity));
    }

    @Override
    public void deleteImage(Long id) {
        avatarRepository.deleteById(id);
    }

    @Override
    public ProfileResponse changeAvatar(ProfileUpdateAvatar profileUpdateAvatar) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ProfileEntity profile = profileRepository.findById(authentication.getName())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));
        AvatarEntity avatarEntity = avatarRepository.findByProfileId(profile.getId());
        AvatarEntity avatar = avatarRepository.findById(profileUpdateAvatar.getId())
                        .orElseThrow(() -> new CustomRuntimeException(ErrorCode.AVATAR_NOT_FOUND));
        avatar.setProfile(profile);
        if(avatarEntity != null){
            mediaClient.deleteById(avatarEntity.getId());
            avatarRepository.deleteById(avatarEntity.getId());
        }
        avatarRepository.save(avatar);
        return mappingOne(profile);
    }

    @Override
    public ProfileInternalResponse findInternalById(String userId) {
        ProfileEntity profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        System.out.println(userId);
        AvatarEntity avatar = avatarRepository.findByProfileId(userId);
        System.out.println(avatar.getUrl());
        return ProfileInternalResponse.builder()
                .id(userId)
                .userName(profile.getFirstName() + " " + profile.getLastName())
                .avatar(avatar !=  null ? avatar.getUrl() : null)
                .build();
    }

    private ProfileResponse mappingOne(ProfileEntity profileEntity){
        ProfileResponse profileResponse = profileMapper.toProfileResponse(profileEntity);
        AvatarEntity avatar = avatarRepository.findByProfileId(profileEntity.getId());
        profileResponse.setAvatar(avatar != null ? avatar.getUrl() : null);
        return  profileResponse;
    }
}

