package com.travelsmart.location_service.service;

import com.travelsmart.location_service.dto.request.IntroduceRequest;
import com.travelsmart.location_service.dto.request.IntroduceUpdateRequest;
import com.travelsmart.location_service.dto.response.IntroduceResponse;

public interface IntroduceService {
    IntroduceResponse create(IntroduceRequest introduceRequest);

    IntroduceResponse getByLocationId(Long locationId);

    IntroduceResponse update(Long id, IntroduceUpdateRequest updateRequest);
}
