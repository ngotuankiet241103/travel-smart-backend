package com.travelsmart.location_service.entity;

import com.travelsmart.location_service.constant.LocationStatus;
import com.travelsmart.location_service.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "location")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long place_id;
    @Column(columnDefinition = "VARCHAR(50)")
    private String category;
    @Column
    private String display_name;
    @Column(unique = true)
    private String lon;
    @Column(unique = true)
    private String lat;
    @Column(columnDefinition = "VARCHAR(100)")
    private String name;
    @Column(columnDefinition = "VARCHAR(100)")
    private String type;
    @Embedded
    private Address address;
    @Column
    private LocationStatus status;
    @OneToOne
    private LocationImageEntity thumbnail;
    @ManyToMany
    @JoinTable(
            name = "location_collections",
            joinColumns = @JoinColumn(name = "location_place_id"),
            inverseJoinColumns = @JoinColumn(name = "collections_id"))
    private Set<LocationImageEntity> collections;
    @Convert(converter = StringListConverter.class)
    private List<String> boundingbox;
}
