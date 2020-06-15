package com.example.autumn.redis.base;

import java.io.Serializable;

@FunctionalInterface
public interface Functional<T, TResult> extends Serializable {

    /**
     * 应用
     *
     * @param t 参数
     * @return 2017-12-05 16:31:16
     */
    TResult apply(T t);
}
