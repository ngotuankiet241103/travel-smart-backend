package com.travelsmart.location_service.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String housenumber;
    private String road;
    private String quarter;
    private String suburb;
    private String city;
    private String state;
    private String postcode;
    private String country_code;
    private String country;
}
