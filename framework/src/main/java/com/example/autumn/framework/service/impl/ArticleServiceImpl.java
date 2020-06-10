package com.example.autumn.framework.service.impl;

import com.autumn.mybatis.mapper.PageResult;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.data.PageQueryBuilder;
import com.example.autumn.framework.base.AbstractSpEditApplicationService;
import com.example.autumn.framework.base.BaseIdDto;
import com.example.autumn.framework.dto.Article.ArticleInput;
import com.example.autumn.framework.dto.Article.ArticleOutput;
import com.example.autumn.framework.dto.Article.ArticleSelectDto;
import com.example.autumn.framework.entities.Article;
import com.example.autumn.framework.repositories.ArticleRepository;
import com.example.autumn.framework.service.ArticleService;
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
        ArticleRepository t = getQueryRepository();
        Class<ArticleOutput> a = this.getOutputItemClass();
        return query.toPageResult(t, a, this::itemConvertHandle);
    }
}

