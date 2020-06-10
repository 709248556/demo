package com.example.autumn.framework.base;

import com.autumn.domain.entities.Entity;

import java.util.Date;

/**
 * @author Vincent
 * @date 2020/6/3
 */
public interface BaseEntity extends Entity<Long> {

    Date getCreatedAt();

    void setCreatedAt(Date time);

    Date getUpdatedAt();

    void setUpdatedAt(Date time);

}
