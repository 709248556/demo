package com.example.autumn.redis.service;

import com.autumn.domain.entities.Entity;
import com.example.autumn.redis.base.ScoreFunction;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2020-06-11 14:38
 **/
public interface RedisService<T extends Entity<Long>> {

    /**
     * 设置值
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 设置值
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时
     * @param unit    时间单位
     */
    void set(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 获取值
     *
     * @param key 键
     */
    <T> T get(String key);

    /**
    * @Description: 单个添加到ZSort
    * @Param: [key, entity]
    * @Param: [scoreFunction] 分数
    * @Return void
    */
    void zSet(String key, T entity, ScoreFunction<Long> scoreFunction);


    /**
     * @Description: 添加集合到ZSort
     * @Param: [key, list]
     * @Param: [scoreFunction] 分数
     * @Return void
     */
    void zSetList(String key, List<T> list);

    /***
    * @Description: 获取key对应ZSet集合的大小
    * @Param: [key]
    * @Return void
    */
    Long size(String key);
}
