package com.example.autumn.redis.test;

import com.example.autumn.redis.base.Functional;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2020-06-15 16:31
 **/
public class Test<TChildren, TNameFunc> {
    private Functional<TNameFunc, TChildren> abc;

    /**
     * 创建列表达式
     *
     * @param bdc 名称或列函数
     * @return
     */
    public TChildren createColumnExpression(TNameFunc bdc) {
        return abc.apply(bdc);
    }

    public TChildren eq(TNameFunc name, Object value) {
        return null;
    }
}
