package com.travelsmart.recommend.mapper;

import com.travelsmart.event.dto.CheckInRequest;
import com.travelsmart.recommend.entity.CheckInEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CheckInMapper {
    CheckInEntity toCheckInEntity(CheckInRequest checkIn);
}
