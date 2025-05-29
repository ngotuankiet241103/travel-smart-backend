package com.travelsmart.recommend.repository.httpclient;

import com.travelsmart.recommend.dto.request.httpclient.RecommendHttpRequest;
import com.travelsmart.recommend.dto.response.httpclient.RecommendHttpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "suggest-service", url = "${feign.url.recommend}/suggests")
public interface RecommendClient {
    @PostMapping
    List<RecommendHttpResponse> getRecommends(@RequestBody RecommendHttpRequest data);
}
