package com.travelsmart.location_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Entity
@Table(name = "introduce_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntroduceLocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToMany
    @JoinTable(
            name = "introduce_collections",
            joinColumns = @JoinColumn(name = "introduce_id"),
            inverseJoinColumns = @JoinColumn(name = "collections_id"))
    private Set<LocationImageEntity> collections;
    @OneToOne
    private LocationEntity location;
}
