package com.travelsmart.profile_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileEntity {
    @Id
    private String id;
    @Column(unique = true,columnDefinition = "VARCHAR(40)")
    private String email;
    @Column(columnDefinition = "NVARCHAR(30)")
    private String firstName;
    @Column(columnDefinition = "NVARCHAR(30)")
    private String lastName;

}
