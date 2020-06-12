package com.example.autumn.framework.test;

import java.io.Serializable;

@FunctionalInterface
public interface Functional<T, TResult> extends Serializable {

    /**
     * 应用
     *
     * @param t 参数
     * @return 2017-12-05 16:31:16
     */
    void apply(String t);
}
