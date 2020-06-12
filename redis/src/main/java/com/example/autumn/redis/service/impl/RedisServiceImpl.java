package com.example.autumn.redis.service.impl;

import com.autumn.redis.AutumnRedisTemplate;
import com.example.autumn.redis.base.Score;
import com.example.autumn.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2020-06-11 14:46
 **/
@Service
public class RedisServiceImpl<T> implements RedisService {

    @Autowired
    private AutumnRedisTemplate autumnRedisTemplate;

    /**
     * 设置值
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void set(String key, Object value) {
        autumnRedisTemplate.opsForCustomValue().set(key,value);
    }

    /**
     * 设置值
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时
     * @param unit    时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        autumnRedisTemplate.opsForCustomValue().set(key, value, timeout, unit);
    }

    /**
     * 获取值
     *
     * @param key 键
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key) {
        return autumnRedisTemplate.opsForCustomValue().get(key);
    }

    /**
     * 获取值并删除
     *
     * @param key 键
     */
    @SuppressWarnings("unchecked")
    public <T> T getByDelete(String key) {
        return autumnRedisTemplate.opsForCustomValue().getAndDelete(key);
    }

    public void zSetList(String key, List<T> list, Score score) {
        Set<ZSetOperations.TypedTuple<T>> set = new HashSet<ZSetOperations.TypedTuple<T>>();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()){
            T entity = iterator.next();
            ZSetOperations.TypedTuple<T> objectTypedTuple1 = new DefaultTypedTuple<T>(entity,score.apply());
        }

        autumnRedisTemplate.opsForZSet().add(key,set);
    }

    public void zSet(String key, T entity,double score) {
        autumnRedisTemplate.opsForZSet().add(key,entity,score);
    }
}
