package com.identity_service.identity.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.identity_service.identity.service.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BaseRedisImplService<T> implements BaseRedisService<T> {
    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    @Override
    public void clear(String hash_key) {
        Set<String> keysToDelete = redisTemplate.keys(hash_key + "*");

        if (keysToDelete != null && !keysToDelete.isEmpty()) {
            redisTemplate.delete(keysToDelete);
        }
    }

    @Override
    public T get(String hash_key) throws JsonProcessingException {
        String json = (String) redisTemplate.opsForValue().get(hash_key);
        System.out.println(hash_key);
        return json == null ? null : redisObjectMapper.readValue(json, new TypeReference<T>() {});
    }

    @Override
    public void save(String hash_key, T data) throws JsonProcessingException {
        String json = redisObjectMapper.writeValueAsString(data);
        System.out.println(hash_key);
        System.out.println(json);
        redisTemplate.opsForValue().set(hash_key,json);
    }

    @Override
    public void save(String hash_key, T data, long timeExpired, TimeUnit timeUnit) throws JsonProcessingException {
        String json = redisObjectMapper.writeValueAsString(data);
        redisTemplate.opsForValue().set(hash_key,json,timeExpired,timeUnit);
    }


    public final String getKey(Pageable pageable,String HASH_KEY){
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Sort sort = pageable.getSort();
        String sortDirection = sort.getOrderFor("id")
                .getDirection() == Sort.Direction.ASC ? "asc" : "desc";
        String key = String.format(HASH_KEY + ":%d:%d:%s",pageNumber,pageSize,sortDirection);
        return key;
    }
}
