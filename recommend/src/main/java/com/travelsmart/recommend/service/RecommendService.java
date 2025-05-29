package com.travelsmart.recommend.service;

import com.travelsmart.recommend.dto.request.RecommendRequest;
import com.travelsmart.recommend.dto.response.RecommendResponse;

import java.util.List;

public interface RecommendService {
   RecommendResponse getRecommend(RecommendRequest recommendRequest);
}
