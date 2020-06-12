package com.example.autumn.redis.service.impl;

import com.autumn.mybatis.mapper.PageResult;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.data.PageQueryBuilder;
import com.example.autumn.redis.Article.ArticleInput;
import com.example.autumn.redis.Article.ArticleOutput;
import com.example.autumn.redis.Article.ArticleSelectDto;
import com.example.autumn.redis.base.AbstractSpEditApplicationService;
import com.example.autumn.redis.base.BaseIdDto;
import com.example.autumn.redis.entities.Article;
import com.example.autumn.redis.repositories.ArticleRepository;
import com.example.autumn.redis.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Description:
 * @author yanlianglong
 * @date 2020-6-9
 */
@Service
public class ArticleServiceImpl extends AbstractSpEditApplicationService<
        Article,
        ArticleRepository,
        Article, ArticleRepository,
        ArticleInput, ArticleInput,
        ArticleOutput, ArticleOutput>
        implements ArticleService {

    @Override
    public String getModuleName() {
        return "文章管理";
    }

    @Override
    protected void queryByOrder(EntityQueryWrapper<Article> query) {
        query.lambda().orderByDescending(Article::getCreatedAt);
    }

    /**
     * 添加之前的处理
     *
     * @param input
     * @param query
     * @return
     */
    @Override
    protected Article addBefore(ArticleInput input, EntityQueryWrapper<Article> query) {
        Article article = super.addBefore(input, query);

        return article;
    }

    /**
    * @Description: 删除
    * @Author: yanlianglong
    * @Date: 2020-6-9
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleOutput delete(BaseIdDto input) {
        Article article = this.getEntity(input.getId());
        this.getRepository().update(article);
        return AutoMapUtils.map(article, ArticleOutput.class);
    }

    /***
    * @Description: 分页查询列表
    * @Author: yanlianglong
    * @Date: @Date: 2020-6-9
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResult<ArticleOutput> queryListForCusPage(ArticleSelectDto input) {
        Class<Article> c = this.getQueryEntityClass();
        PageQueryBuilder<Article> query = new PageQueryBuilder<>(c);
        this.generateQueryListColumn(query.getQuery());
        this.systemByCriteria(query.getQuery());
        this.queryByOrder(query.getQuery());
        query.page(input.getCurrentPage(), input.getPageSize());
        return query.toPageResult(getQueryRepository(), this.getOutputItemClass(), this::itemConvertHandle);
    }
}

