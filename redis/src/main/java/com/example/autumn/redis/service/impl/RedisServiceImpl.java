package com.example.autumn.redis.service.impl;

import com.autumn.domain.entities.Entity;
import com.autumn.redis.AutumnRedisTemplate;
import com.example.autumn.redis.base.ScoreFunction;
import com.example.autumn.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
     * @Description: 单个添加到ZSort,根据ID排序
     */
    @Override
    public void zSetOrderById(String key, T entity, ScoreFunction<Long> scoreFunction) {
        autumnRedisTemplate.opsForZSet().add(key, entity, transform(scoreFunction.apply()));
    }

    /**
     * @Description: 添加集合到ZSort,根据ID排序
     */
    @Override
    public void zSetListOrderById(String key, List<T> list) {
        autumnRedisTemplate.executePipelined(new RedisCallback<Long>() {
            @Nullable
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                list.forEach(item->{
                    byte[] rawKey = autumnRedisTemplate.opsForCustomValue().serializeKey(key);
                    byte[] rawValue = autumnRedisTemplate.opsForCustomValue().serializeValue(item);
                    connection.zAdd(rawKey,transform(item.getId()),rawValue);
                });
                return null;
            }
        });
    }

    /**
     * @Description: 添加集合到ZSort
     */
//    @Override
//    public void zSetListFuntion(String key, List<T> list,ScoreFunction<List> scoreFunction) {
//        autumnRedisTemplate.executePipelined(new RedisCallback<Long>() {
//            @Nullable
//            @Override
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.openPipeline();
//                list.forEach(item->{
//                    byte[] rawKey = autumnRedisTemplate.opsForCustomValue().serializeKey(key);
//                    byte[] rawValue = autumnRedisTemplate.opsForCustomValue().serializeValue(item);
//                    connection.zAdd(rawKey,item.getId(),rawValue);
//                });
//                return null;
//            }
//        });
//    }

    /**
     * @Description: 获取key对应ZSet集合的大小
     */
    @Override
    public Long size(String key) {
        return autumnRedisTemplate.opsForZSet().zCard(key);
    }

    @Override
    public List<T> reverseRangeWithScores(String key,Integer currentPage,Integer pageSize) {
        Long begin = Long.valueOf((currentPage-1)*pageSize);
        Long end = Long.valueOf(currentPage*pageSize-1);
        Set<ZSetOperations.TypedTuple<Object>> set =  autumnRedisTemplate.opsForZSet().reverseRangeWithScores(key,begin,end);
        List<T> list = new ArrayList(set);
        return list;
    }

    private double transform(Long a){
        return new BigDecimal(a).subtract(new BigDecimal(190000000000000000L)).divide(new BigDecimal(100000000)).setScale(8).doubleValue();
    }
}
