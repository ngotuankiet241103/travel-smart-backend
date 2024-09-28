package com.travelsmart.media_service.service.impl;

import com.travelsmart.media_service.dto.request.MediaRequest;
import com.travelsmart.media_service.dto.response.MediaResponse;
import com.travelsmart.media_service.entity.Media;
import com.travelsmart.media_service.mapper.MediaMapper;
import com.travelsmart.media_service.repository.MediaRepository;
import com.travelsmart.media_service.service.FileService;
import com.travelsmart.media_service.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final FileService fileService;
    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    @Override
    public MediaResponse uploadFile(MediaRequest mediaRequest) throws IOException {
        MediaType mediaType = MediaType.valueOf(Objects.requireNonNull(mediaRequest.getFile().getContentType()));
        if (!(MediaType.IMAGE_PNG.equals(mediaType)
                || MediaType.IMAGE_JPEG.equals(mediaType)
                || MediaType.IMAGE_GIF.equals(mediaType))) {
            throw new RuntimeException("Not support file ");
        }
        String publicId = UUID.randomUUID().toString();
        String url =  fileService.uploadCloud(mediaRequest.getFile(), publicId);
        Media media = Media.builder()
                .publicId(publicId)
                .url(url)
                .caption(mediaRequest.getCaption())
                .mediaType(mediaType.getType())
                .build();
        return mappingOne(mediaRepository.save(media));
    }

    @Override
    public MediaResponse findById(Long id) {
        return mappingOne(mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found")));
    }

    @Override
    public void deleteById(Long id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));
        fileService.removeImage(media.getPublicId());
        mediaRepository.deleteById(media.getId());
    }

    private MediaResponse mappingOne(Media media){
        return mediaMapper.toMediaResponse(media);
    }
}
