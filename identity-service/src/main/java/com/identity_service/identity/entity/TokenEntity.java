package com.identity_service.identity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenEntity {
    @Id
    private String token;
    @Column
    private boolean isExpired;
    @ManyToOne
    private UserEntity user;
}
