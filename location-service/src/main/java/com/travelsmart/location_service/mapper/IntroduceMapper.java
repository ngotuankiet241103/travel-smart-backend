package com.travelsmart.location_service.mapper;

import com.travelsmart.location_service.dto.request.IntroduceRequest;
import com.travelsmart.location_service.dto.response.IntroduceResponse;
import com.travelsmart.location_service.entity.IntroduceLocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IntroduceMapper {
    IntroduceLocationEntity toIntroduceLocationEntity(IntroduceRequest introduceRequest);
    @Mapping(target = "collections",ignore = true)
    IntroduceResponse toIntroduceResponse(IntroduceLocationEntity introduceLocationEntity);
}
