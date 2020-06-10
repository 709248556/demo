package com.example.autumn.framework.service;

import com.autumn.application.service.EditApplicationService;
import com.autumn.mybatis.mapper.PageResult;
import com.example.autumn.framework.base.BaseIdDto;
import com.example.autumn.framework.dto.Article.ArticleInput;
import com.example.autumn.framework.dto.Article.ArticleOutput;
import com.example.autumn.framework.dto.Article.ArticleSelectDto;

/**
 * @Description:
 * @author yanlianglong
 * @date 2020-6-9
 */
public interface ArticleService extends
        EditApplicationService<Long, ArticleInput, ArticleInput, ArticleOutput, ArticleOutput>{

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

