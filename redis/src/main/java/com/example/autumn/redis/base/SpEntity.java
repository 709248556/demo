package com.example.autumn.redis.base;

import com.autumn.domain.entities.Entity;

import java.util.Date;

/**
 * @author Vincent
 * @date 2020/6/3
 */
public interface SpEntity extends Entity<Long> {

    Date getCreatedAt();

    void setCreatedAt(Date time);

    Date getUpdatedAt();

    void setUpdatedAt(Date time);

}
