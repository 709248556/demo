package com.example.autumn.redis.entities;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.domain.entities.AbstractDefaultEntity;
import com.autumn.domain.entities.auditing.SoftDelete;
import com.autumn.mybatis.mapper.annotation.Index;
import com.example.autumn.redis.base.BaseEntity;
import com.example.autumn.redis.base.SpEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Jekin
 * @date 6/3/20 3:16 下午
 */
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "sp_article")
public class Article extends AbstractDefaultEntity{


    private static final long serialVersionUID = -2024849390499317745L;

    @GeneratedValue(strategy = GenerationType.TABLE)
    @Override
    @LogMessage(name = "文章id", order = 1)
    public Long getId() {
        return super.getId();
    }

    /**
     * 是否删除
     */
    private boolean delete;
    /**
     * 标题
     */
    @LogMessage(name = "标题", order = 2)
    private String title;

    /**
     * 内容类型，1：图文，2：视频
     */
    @LogMessage(name = "内容类型，1：图文，2：视频", order = 3)
    private Integer contentType;

    /**
     * 内容概要
     */
    @LogMessage(name = "内容概要", order = 4)
    private String intro;

    /**
     * 详情
     */
    @LogMessage(name = "详情", order = 5)
    private String detail;

    /**
     * 封面图
     */
    @Index
    @LogMessage(name = "封面图", order = 6)
    private Long fileId;

    /**
     * 作者
     */
    @LogMessage(name = "作者", order = 7)
    private String author;

    /**
     * 是否原创，1：原创，2：非原创
     */
    @LogMessage(name = "是否原创，1：原创，2：非原创", order = 8)
    private Integer isOriginal;

    /**
     * 类型，1：资讯，2：新闻动态，3：专家讲解，4：辟谣信息
     */
    @LogMessage(name = "类型，1：资讯，2：新闻动态，3：专家讲解，4：辟谣信息", order = 9)
    private Integer type;

    /**
     * 分类id
     */
    @Index
    @LogMessage(name = "分类id", order = 10)
    private Long newsCategoryId;

    /**
     * 活动状态，1：待发布，2：已发布，3：撤回
     */
    @LogMessage(name = "活动状态，1：待发布，2：已发布，3：撤回", order = 11)
    private Integer sendStatus;

    /**
     * 提审状态，1：草稿，2：提审中，3：撤回
     */
    @LogMessage(name = "提审状态，1：草稿，2：提审中，3：撤回", order = 12)
    private Integer submitCheckStatus;

    /**
     * created_at
     */
    @LogMessage(name = "创建日期", order = 13)
    private Date createdAt;

    /**
     * updated_at
     */
    @LogMessage(name = "更新日期", order = 14)
    private Date updatedAt;
}
