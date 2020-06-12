package com.example.autumn.redis.base;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2020-06-10 10:43
 **/
@FunctionalInterface
public interface Score<T> {
    /**
     * 应用
     */
    T apply();
}
