package com.travelsmart.recommend.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelsmart.recommend.dto.request.RecommendRequest;
import com.travelsmart.recommend.dto.request.httpclient.RecommendHttpRequest;
import com.travelsmart.recommend.dto.response.RecommendResponse;
import com.travelsmart.recommend.entity.CheckInEntity;
import com.travelsmart.recommend.repository.CheckInRepository;
import com.travelsmart.recommend.repository.httpclient.LocationClient;
import com.travelsmart.recommend.repository.httpclient.RecommendClient;
import com.travelsmart.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {
    private final RecommendClient recommendClient;
    private final CheckInRepository checkInRepository;
    private final LocationClient locationClient;

    @Override
    public RecommendResponse getRecommend(RecommendRequest recommendRequest) {
        String userId= SecurityContextHolder.getContext().getAuthentication().getName();
        List<CheckInEntity> checkInEntities = checkInRepository.findByUserId(userId);
        Map<String,Integer> table = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        RecommendHttpRequest request = new RecommendHttpRequest();
        checkInEntities.stream()
                .forEach(checkInEntity -> {
                    if(table.containsKey(checkInEntity.getType().toString())){
                        Integer total = table.get(checkInEntity.getType().toString());
                        table.put(checkInEntity.getType().toString(), total + 1);
                    }
                    else{
                        table.put(checkInEntity.getType().toString(),1);
                    }
                });
        Map<String, Object> obj = objectMapper.convertValue(request, new TypeReference<Map<String, Object>>() {});
        new ArrayList<>(obj.keySet()).stream()
                .forEach(key -> {
                    if(table.containsKey(key)){
                        obj.put(key,table.get(key));
                    }

                });
        request = objectMapper.convertValue(obj,RecommendHttpRequest.class);
        request.setCurrent_lat(Float.parseFloat(recommendRequest.getCurrent_lat()));
        request.setCurrent_lng(Float.parseFloat(recommendRequest.getCurrent_lon()));

        return RecommendResponse.builder()
                .recommends(recommendClient.getRecommends(request).stream()
                        .map(data -> locationClient.getLocationById(data.getPlace_id()).getResult()).collect(Collectors.toList()))
                .build();
    }
}
