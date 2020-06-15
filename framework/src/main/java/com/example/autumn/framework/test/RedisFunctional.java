package com.example.autumn.framework.test;

import java.io.Serializable;

@FunctionalInterface
public interface RedisFunctional<T, TResult> extends Serializable {

    /**
     * 应用
     */
    TResult apply();
}
