package com.travelsmart.trip_service.entity;

import com.travelsmart.trip_service.constant.TripPermission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_trip")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTripEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private TripPermission permission;
    @Column
    private String userId;
    @ManyToOne
    private TripEntity trip;
}
