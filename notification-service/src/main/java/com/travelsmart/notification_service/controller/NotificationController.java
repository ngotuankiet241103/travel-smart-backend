package com.travelsmart.notification_service.controller;

import com.travelsmart.event.dto.NotificationEvent;
import com.travelsmart.notification_service.dto.request.EmailRequest;
import com.travelsmart.notification_service.dto.request.Recipient;
import com.travelsmart.notification_service.service.EmailService;

import com.travelsmart.saga.notication.command.NotificationCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final EmailService emailService;
    private final KafkaTemplate<String,Object> template;
    @KafkaListener(topics = "notification",groupId = "notification-group")
    void notificationEvent(NotificationCommand notificationCommand){
        System.out.println(notificationCommand);
        if(notificationCommand.getChanel().equals("EMAIL")){
            emailService.sendEmail(EmailRequest.buildEmailRequest(notificationCommand));
            if(!notificationCommand.getNextEvent().isEmpty()){
                template.send(notificationCommand.getNextEvent(),"Send email success");
            }
        }
    }


}
