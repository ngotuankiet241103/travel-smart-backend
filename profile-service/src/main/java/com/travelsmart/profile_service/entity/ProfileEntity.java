package com.travelsmart.profile_service.entity;

import com.travelsmart.profile_service.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Convert(converter = StringListConverter.class)
    private List<String> hobbies;
}
