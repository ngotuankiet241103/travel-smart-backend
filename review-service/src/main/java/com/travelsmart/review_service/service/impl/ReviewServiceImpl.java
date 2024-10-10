package com.travelsmart.review_service.service.impl;

import com.travelsmart.review_service.dto.request.ReviewImageRequest;
import com.travelsmart.review_service.dto.request.ReviewRequest;
import com.travelsmart.review_service.dto.request.ReviewUpdateRequest;
import com.travelsmart.review_service.dto.response.PageableResponse;
import com.travelsmart.review_service.dto.response.Paging;
import com.travelsmart.review_service.dto.response.ReviewImageResponse;
import com.travelsmart.review_service.dto.response.ReviewResponse;
import com.travelsmart.review_service.dto.response.httpclient.MediaHttpResponse;
import com.travelsmart.review_service.entity.ReviewEntity;
import com.travelsmart.review_service.entity.ReviewImageEntity;
import com.travelsmart.review_service.exception.CustomRuntimeException;
import com.travelsmart.review_service.exception.ErrorCode;
import com.travelsmart.review_service.mapper.ReviewMapper;
import com.travelsmart.review_service.repository.ReviewImageRepository;
import com.travelsmart.review_service.repository.ReviewRepository;
import com.travelsmart.review_service.repository.httpclient.MediaClient;
import com.travelsmart.review_service.repository.httpclient.ProfileClient;
import com.travelsmart.review_service.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewMapper reviewMapper;
    private final ProfileClient profileClient;
    private final MediaClient mediaClient;
    @Override
    public ReviewResponse create(ReviewRequest reviewRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        ReviewEntity reviewEntity = reviewMapper.toReviewEntity(reviewRequest);
        reviewEntity.setUserId(userId);
        reviewRepository.save(reviewEntity);
        List<ReviewImageEntity> images = null;
        if(reviewRequest.getImageIds() != null){
          images =   reviewRequest.getImageIds().stream().map(imageId -> {
                ReviewImageEntity reviewImageEntity = reviewImageRepository.findById(imageId)
                        .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND));
                reviewImageEntity.setReview(reviewEntity);
                return  reviewImageEntity;
            }).toList();
           reviewImageRepository.saveAll(images);
        }
        return mappingOne(reviewEntity,images);
    }

    @Override
    public PageableResponse<List<ReviewResponse>> getByLocationId(Long locationId, Pageable pageable) {
        Page<ReviewEntity> page = reviewRepository.findByLocationId(pageable,locationId);
        Paging paging = Paging.buildPaging(pageable,page.getTotalPages());
        return PageableResponse.<List<ReviewResponse>>builder()
                .paging(paging)
                .data(mappingList(page.getContent()))
                .build();
    }

    @Override
    public ReviewResponse update(Long id, ReviewUpdateRequest reviewUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.REVIEW_NOT_FOUND));
        if(!review.getUserId().equals(userId)){
            return null;
        }
        List<ReviewImageEntity> imageEntities = null ;
        List<ReviewImageEntity> images = reviewImageRepository.findByReviewId(id);
        if(reviewUpdateRequest.getImageIds()!= null){
            if(!images.isEmpty()){
                Map<Long,Long> table = reviewUpdateRequest.getImageIds().stream()
                        .collect(Collectors.toMap(item->item,item->item));

                List<ReviewImageEntity> deleteImages = images.stream().filter(image -> !table.containsKey(image.getId()) ).toList();
                System.out.println(deleteImages);
                deleteImages.forEach(image -> deleteImageById(image.getId()));
            }
            imageEntities = reviewUpdateRequest.getImageIds().stream()
                    .map(imageId -> {
                        System.out.println(imageId);
                        ReviewImageEntity reviewImageEntity = reviewImageRepository.findById(imageId)
                                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND));
                        reviewImageEntity.setReview(review);
                        return  reviewImageRepository.save(reviewImageEntity);
                    })
                    .toList();
            reviewImageRepository.saveAll(imageEntities);
        }

        if(imageEntities != null){
            reviewImageRepository.saveAll(imageEntities);
        }
        review.setContent(reviewUpdateRequest.getContent());
        review.setStarRate(reviewUpdateRequest.getStarRate());
        reviewRepository.save(review);
        return mappingOne(review,imageEntities);
    }

    @Override
    public ReviewImageResponse uploadImage(ReviewImageRequest reviewImageRequest) {
        MediaHttpResponse mediaHttpResponse = mediaClient.uploadFile(reviewImageRequest.getFile()).getResult();
        ReviewImageEntity reviewImageEntity = ReviewImageEntity.builder()
                .id(mediaHttpResponse.getId())
                .url(mediaHttpResponse.getUrl())
                .build();
        reviewImageRepository.save(reviewImageEntity);
        return ReviewImageResponse.builder()
                .id(reviewImageEntity.getId())
                .url(reviewImageEntity.getUrl())
                .build();
    }

    @Override
    public void deleteImage(Long id) {
        mediaClient.deleteById(id);
        reviewImageRepository.deleteById(id);
    }

    @Override
    public Double getAverageRatingByLocation(Long locationId) {
        return reviewRepository.averageRatingByLocation(locationId);
    }

    @Override
    public ReviewResponse getDetailReviewByUser(Long locationId, String name) {
        ReviewEntity reviewEntity = reviewRepository.findByLocationIdAndUserId(locationId,name);
        List<ReviewImageEntity> reviewImageEntities = reviewImageRepository.findByReviewId(reviewEntity.getId());
        return mappingOne(reviewEntity,reviewImageEntities);
    }

    private void deleteImageById(Long id){
        mediaClient.deleteById(id);
        reviewImageRepository.deleteById(id);
    }

    private ReviewResponse mappingOne(ReviewEntity reviewEntity,List<ReviewImageEntity> images) {
        ReviewResponse response = reviewMapper.toReviewResponse(reviewEntity);
        response.setUser(profileClient.getUserById(reviewEntity.getUserId()).getResult());
        response.setImages(images != null ? images.stream().map(image -> {
            return ReviewImageResponse.builder().id(image.getId()).url(image.getUrl()).build();
        }).toList() : null);
        return response;
    }
    private List<ReviewResponse> mappingList(List<ReviewEntity> e){

        return e.stream()
                .map(reviewEntity -> {
                    List<ReviewImageEntity> images = reviewImageRepository.findByReviewId(reviewEntity.getId());
                    return mappingOne(reviewEntity,images);
                })
                .toList();
    }
}
