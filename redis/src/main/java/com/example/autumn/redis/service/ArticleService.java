package com.example.autumn.redis.service;

import com.autumn.application.service.EditApplicationService;
import com.autumn.mybatis.mapper.PageResult;
import com.example.autumn.redis.article.ArticleInput;
import com.example.autumn.redis.article.ArticleOutput;
import com.example.autumn.redis.article.ArticleSelectDto;
import com.example.autumn.redis.base.BaseIdDto;

/**
 * @Description:文章管理服务
 * @author yanlianglong
 * @date 2020-6-10
 */
public interface ArticleService extends
        EditApplicationService<Long, ArticleInput, ArticleInput, ArticleOutput, ArticleOutput> {

    /**
     * 删除
     *
     * @param input
     * @return
     */
    ArticleOutput deleteById(BaseIdDto input);

    /**
     * 分页查找列表
     *
     * @param input
     * @return
     */
    PageResult<ArticleOutput> queryListPage(ArticleSelectDto input);

}

