package com.travelsmart.recommend.entity;

import com.travelsmart.recommend.constrant.LocationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "check_in")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userId;
    @Column
    private LocationType type;
    @Column
    private Date checkInTime;

}
