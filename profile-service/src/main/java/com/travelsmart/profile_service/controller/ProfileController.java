package com.travelsmart.profile_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelsmart.event.dto.EmailRequest;
import com.travelsmart.event.dto.ProfileCommand;
import com.travelsmart.event.dto.ProfileRequest;
import com.travelsmart.profile_service.dto.request.AvatarRequest;
import com.travelsmart.profile_service.dto.request.HobbyRequest;
import com.travelsmart.profile_service.dto.request.ProfileUpdateAvatar;
import com.travelsmart.profile_service.dto.request.ProfileUpdateRequest;
import com.travelsmart.profile_service.dto.response.*;
import com.travelsmart.profile_service.entity.LocationType;
import com.travelsmart.profile_service.service.ProfileService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.shaded.com.google.protobuf.Api;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
//    private final KafkaTemplate<String,Object> template;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String,Object> template;
    @Operation(summary = "Update information of profile", description = "Returns single profile")
    @PutMapping("/profiles/{id}")
    public ApiResponse<ProfileResponse> update(@PathVariable("id") String id,
                                               @RequestBody ProfileUpdateRequest profileUpdateRequest){
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.update(id,profileUpdateRequest))
                .build();
    }
    @Operation(summary = "Get user profile", description = "Returns single profile")
    @GetMapping("/my-profile")
    public ApiResponse<ProfileResponse> getMyProfile(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(userId);
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.findById(userId))
                .build();
    }
    @Operation(summary = "Create profile user", description = "Returns single profile")
    @PostMapping("/create-profile")
    public ApiResponse<ProfileResponse> create(@RequestBody ProfileCommand profileCommand){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.findById(userId))
                .build();
    }
    @Operation(summary = "Upload image", description = "Returns single image")
    @PostMapping("/upload")
    public ApiResponse<AvatarResponse> updateLoadImage(@ModelAttribute AvatarRequest avatarRequest) {
        return ApiResponse.<AvatarResponse>builder()
                .result(profileService.uploadAvatar(avatarRequest))
                .build();

    }
    @Operation(summary = "Change avatar", description = "Returns single profile")
    @PutMapping("/change-avatar")
    public ApiResponse<ProfileResponse> changeAvatar(@RequestBody ProfileUpdateAvatar profileUpdateAvatar) {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.changeAvatar(profileUpdateAvatar))
                .build();

    }

    @Operation(summary = "Change avatar", description = "Returns single profile")
    @DeleteMapping("/image/{id}")
    public ApiResponse<Void> deleteImage(@PathVariable("id") Long id) {
        profileService.deleteImage(id);
        return ApiResponse.<Void>builder().build();
    }
    @Hidden
    @GetMapping("/internal/profile/{user-id}")
    public ApiResponse<ProfileInternalResponse> getProfileById(@PathVariable("user-id") String userId){
        return ApiResponse.<ProfileInternalResponse>builder()
                .result(profileService.findInternalById(userId))
                .build();
    }
    @PutMapping("/hobbies")
    public ApiResponse<ProfileResponse> updateHobbies(@RequestBody HobbyRequest hobbyRequest){
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.updateHobbies(hobbyRequest))
                .build();
    }
    @GetMapping("/hobbies")
    public ApiResponse<List<LocationTypeResponse>> getHobbies(){
        return ApiResponse.<List<LocationTypeResponse>>builder()
                .result(LocationType.getHobbies())
                .build();
    }
    @KafkaListener(topics = "createUser-success",groupId = "profile-group")
    public void createProfile(ProfileCommand object){
        System.out.println(object);
        profileService.create(object);
        EmailRequest emailRequest = new EmailRequest(object.userId());
        template.setMessageConverter(new StringJsonMessageConverter(objectMapper));
        template.send("profileUser-success",true);
    }

//    @KafkaListener(topics = "createUser-success",groupId = "profile-group")
//    public void createProfile(ProfileRequest object){
//
//    }

}
