package com.travelsmart.recommend.service.impl;

import com.travelsmart.event.dto.CheckInRequest;
import com.travelsmart.recommend.constrant.LocationType;
import com.travelsmart.recommend.dto.request.CheckRequest;
import com.travelsmart.recommend.entity.CheckInEntity;
import com.travelsmart.recommend.repository.CheckInRepository;
import com.travelsmart.recommend.repository.httpclient.LocationClient;
import com.travelsmart.recommend.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CheckInServiceImpl implements CheckInService {
    private final CheckInRepository checkInRepository;
    private final LocationClient locationClient;

    @Override
    public void createNewCheckIn(CheckInRequest checkIn) {

        saveCheckIn(checkIn.getType(), checkIn.getUserId());

    }
    private void saveCheckIn(LocationType type,String userId){
        CheckInEntity checkInEntity = new CheckInEntity();
        checkInEntity.setType(type);
        checkInEntity.setUserId(userId);
        checkInEntity.setCheckInTime(new Date());
        checkInRepository.save(checkInEntity);
    }

    @Override
    public Void createNewCheckIn(CheckRequest checkRequest) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        LocationType type = locationClient.getLocationById(checkRequest.getPlace_id()).getResult().getType();
        saveCheckIn(type,userId);
        return null;

    }
}
