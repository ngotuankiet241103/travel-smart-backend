package com.travelsmart.notification_service.dto.request;


import com.travelsmart.saga.notication.command.NotificationCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private Sender sender;
    private List<Recipient> to;
    private String htmlContent;
    private String subject;
    public static EmailRequest buildEmailRequest(NotificationCommand notificationCommand){

        new ArrayList<>(notificationCommand.getParams().keySet())
                .forEach(key -> {
                    String content =
                            notificationCommand.getTemplateCode().replace("{" + key +"}",
                            notificationCommand.getParams().get(key).toString());
                    notificationCommand.setTemplateCode(content);
                } );
        List<Recipient> to  = new ArrayList<>();
        to.add(new Recipient(notificationCommand.getRecipient()));
        return EmailRequest.builder()
                .sender(new Sender("demoapp2411@gmail.com","Tuan Kiet"))
                .to(to)
                .htmlContent(notificationCommand.getTemplateCode())
                .subject(notificationCommand.getParams().get("subject").toString())
                .build();
    }
}
