package com.travelsmart.notification_service.controller;

import com.travelsmart.notification_service.dto.request.EmailRequest;
import com.travelsmart.notification_service.dto.request.Recipient;
import com.travelsmart.notification_service.dto.response.ApiResponse;
import com.travelsmart.notification_service.dto.response.EmailResponse;
import com.travelsmart.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class EmailController {
    private final EmailService emailService;
    @PostMapping("/stmp/email")
    public ApiResponse<EmailResponse> sendEmail(@RequestBody EmailRequest emailRequest){
        return ApiResponse.<EmailResponse>builder()
                .result(emailService.sendEmail(emailRequest))
                .build();
    }
}
