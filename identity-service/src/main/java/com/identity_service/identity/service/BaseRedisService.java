package com.identity_service.identity.service;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.TimeUnit;

public interface BaseRedisService<T> {
    void clear(String hash_key);
    T get(String hash_key) throws JsonProcessingException;
    void save(String hash_key, T data) throws JsonProcessingException;
    void save(String hash_key, T data, long timeExpiredm, TimeUnit timeUnit) throws JsonProcessingException;

}
