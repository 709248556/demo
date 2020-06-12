package com.example.autumn.redis.base;

import java.io.Serializable;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2020-06-10 10:43
 **/
@FunctionalInterface
public interface ScoreFunction<TResult> extends Serializable {
    /**
     * 应用
     */
    TResult apply();
}
