package com.travelsmart.trip_service.entity;

import com.travelsmart.trip_service.constant.TripPermission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Table(name = "trip")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String code;
    @Column
    private Long locationId;
    @Column
    private String image;
    @Column
    private Date startDate;
    @Column
    private Date endDate;

}
