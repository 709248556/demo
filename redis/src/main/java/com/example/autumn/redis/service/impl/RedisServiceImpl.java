package com.example.autumn.redis.service.impl;

import com.autumn.domain.entities.Entity;
import com.autumn.redis.AutumnRedisTemplate;
import com.example.autumn.redis.base.ScoreFunction;
import com.example.autumn.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2020-06-11 14:46
 **/
@Service
public class RedisServiceImpl<T extends Entity<Long>> implements RedisService<T> {

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
        autumnRedisTemplate.opsForCustomValue().set(key, value);
    }

    /**
     * 设置值
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时
     * @param unit    时间单位
     */
    @Override
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
     * @Description: 单个添加到ZSort
     */
    @Override
    public void zSet(String key, T entity, ScoreFunction<Long> scoreFunction) {
        autumnRedisTemplate.opsForZSet().add(key, entity, scoreFunction.apply());
    }

    /**
     * @Description: 添加集合到ZSort
     */
    @Override
    public void zSetList(String key, List<T> list) {
        Set<ZSetOperations.TypedTuple<T>> set = new HashSet<ZSetOperations.TypedTuple<T>>();
        list.forEach(item->{
            this.zSet(key,item,item::getId);
        });
    }


    /**
     * @Description: 获取key对应ZSet集合的大小
     */
    @Override
    public Long size(String key) {
        return autumnRedisTemplate.opsForZSet().size(key);
    }
}
