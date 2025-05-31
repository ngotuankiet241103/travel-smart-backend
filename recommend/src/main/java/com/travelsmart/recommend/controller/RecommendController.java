package com.travelsmart.recommend.controller;

import com.travelsmart.event.dto.CheckInRequest;
import com.travelsmart.recommend.dto.request.CheckRequest;
import com.travelsmart.recommend.dto.request.RecommendRequest;
import com.travelsmart.recommend.dto.response.ApiResponse;
import com.travelsmart.recommend.dto.response.LocationResponse;
import com.travelsmart.recommend.dto.response.RecommendResponse;
import com.travelsmart.recommend.service.CheckInService;
import com.travelsmart.recommend.service.RecommendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommends")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;
    private final CheckInService checkInService;
    @PostMapping
    public ApiResponse<RecommendResponse> getRecommends(@RequestBody @Valid RecommendRequest recommendRequest){
        return ApiResponse.<RecommendResponse>builder()
                .result(recommendService.getRecommend(recommendRequest))
                .build();
    }
    @PostMapping("/check")
    public ApiResponse<Void> createCheck(@RequestBody @Valid CheckRequest checkRequest){
        return ApiResponse.<Void>builder()
                .result(checkInService.createNewCheckIn(checkRequest))
                .build();
    }
    @KafkaListener(topics = "new-checkIn",groupId = "recommend-group")
    public void createProfile(CheckInRequest checkIn){
        System.out.println("get ");
            checkInService.createNewCheckIn(checkIn);
//        System.out.println(object);
//        profileService.create(object);
//        EmailRequest emailRequest = new EmailRequest(object.userId());
//        template.setMessageConverter(new StringJsonMessageConverter(objectMapper));
//        template.send("profileUser-success",true);
    }
}
