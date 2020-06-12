package com.example.autumn.redis.service;

import com.autumn.application.service.EditApplicationService;
import com.autumn.mybatis.mapper.PageResult;
import com.example.autumn.redis.Article.ArticleInput;
import com.example.autumn.redis.Article.ArticleOutput;
import com.example.autumn.redis.Article.ArticleSelectDto;
import com.example.autumn.redis.base.BaseIdDto;

/**
 * @Description:
 * @author yanlianglong
 * @date 2020-6-9
 */
public interface ArticleService extends
        EditApplicationService<Long, ArticleInput, ArticleInput, ArticleOutput, ArticleOutput> {

    /**
     * 删除
     *
     * @param input
     * @return
     */
    ArticleOutput delete(BaseIdDto input);

    /**
     * 分页查找列表
     *
     * @param input
     * @return
     */
    PageResult<ArticleOutput> queryListForCusPage(ArticleSelectDto input);
}

