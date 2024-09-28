package com.travelsmart.profile_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "avatar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvatarEntity {
    @Id
    private Long id;
    @Column
    private String url;
    @OneToOne
    private ProfileEntity profile;
}
