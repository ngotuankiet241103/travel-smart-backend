package com.travelsmart.notification_service.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mail {
    private String email;
    private String name;

    public Mail(String email) {
        this.email = email;
    }
}
