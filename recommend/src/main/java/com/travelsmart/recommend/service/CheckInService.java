package com.travelsmart.recommend.service;

import com.travelsmart.event.dto.CheckInRequest;
import com.travelsmart.recommend.dto.request.CheckRequest;

public interface CheckInService {
    void createNewCheckIn(CheckInRequest checkIn);
    Void createNewCheckIn(CheckRequest checkRequest);
}
