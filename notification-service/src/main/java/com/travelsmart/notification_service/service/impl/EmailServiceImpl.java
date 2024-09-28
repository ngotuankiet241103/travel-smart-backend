package com.travelsmart.notification_service.service.impl;

import com.travelsmart.notification_service.dto.request.EmailRequest;
import com.travelsmart.notification_service.dto.response.EmailResponse;
import com.travelsmart.notification_service.repository.httpclient.EmailClient;
import com.travelsmart.notification_service.service.EmailService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailClient emailClient;
    @Value("${notification.email.brevo-apiKey}")
    private String apiKey;

    @Override
    public EmailResponse sendEmail(EmailRequest emailRequest) {
        try{
            return emailClient.sendEmail(apiKey,emailRequest);
        }
        catch (FeignException.FeignClientException exception){
            System.out.println(exception.getMessage());
        }
        return  null;
    }
}
