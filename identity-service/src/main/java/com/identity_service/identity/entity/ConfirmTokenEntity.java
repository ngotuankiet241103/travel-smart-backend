package com.identity_service.identity.entity;

import com.identity_service.identity.constaint.ConfirmTokenType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "confirm_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmTokenEntity {
    @Id
    private String token;
    @Column
    private ConfirmTokenType type;
    @Column
    private boolean isExpired;
    @Column
    private boolean isConfirm;
    @Column
    private Date timeExpired;
    @ManyToOne
    private UserEntity user;
}
