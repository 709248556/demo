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
import com.example.autumn.framework.service.RedisService;
import com.example.autumn.framework.test.KeyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Action;
import java.util.List;


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

    @Autowired
    private RedisService<ArticleOutput> redisService;

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
        System.out.println("getMethodSign:"+this.getMethodSign());
        System.out.println("this.getKey:"+this.getKey(ArticleSelectDto::getStatus,input.getStatus()));
//        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
//        this.getSignature(Thread.currentThread().getStackTrace()[1].getClass().getEnclosingMethod());
        this.getKey(ArticleSelectDto::getStatus,input.getStatus());
        redisService.set("article13","123456");
        KeyBuilder keyBuilder = new KeyBuilder(this.getMethodSign());
        keyBuilder.eq(ArticleSelectDto::getStatus,input.getStatus()).ne(ArticleSelectDto::getStatus,input.getStatus());
        System.out.println(keyBuilder.getResult());
        Class<Article> c = this.getQueryEntityClass();
        PageQueryBuilder<Article> query = new PageQueryBuilder<>(c);
        this.generateQueryListColumn(query.getQuery());
        this.systemByCriteria(query.getQuery());
        this.queryByOrder(query.getQuery());
        query.page(input.getCurrentPage(), input.getPageSize());
        ArticleRepository t = getQueryRepository();
        Class<ArticleOutput> a = this.getOutputItemClass();
        query.getQuery().lambda().where().eq(Article::getStatus, input.getStatus()).eq(Article::getStatus, input.getStatus());
        return query.toPageResult(t, a, this::itemConvertHandle);
    }
}

