package com.travelsmart.notification_service.service;

import com.travelsmart.notification_service.dto.request.EmailRequest;
import com.travelsmart.notification_service.dto.response.EmailResponse;

public interface EmailService {


    EmailResponse sendEmail(EmailRequest emailRequest);
}
