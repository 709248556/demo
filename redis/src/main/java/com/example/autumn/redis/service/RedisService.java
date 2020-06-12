package com.example.autumn.redis.service;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2020-06-11 14:38
 **/
public interface RedisService {
    void set(String key, Object value);
    <T> T get(String key);
}
