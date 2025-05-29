package com.travelsmart.review_service.dto.response.httpclient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
@Embeddable
@Data
@JsonSerialize
public class Address implements Serializable {
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
