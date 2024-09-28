package com.travelsmart.media_service.mapper;

import com.travelsmart.media_service.dto.response.MediaResponse;
import com.travelsmart.media_service.entity.Media;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    MediaResponse toMediaResponse(Media media);
}
