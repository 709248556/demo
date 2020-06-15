package com.example.autumn.redis.service.impl;

import com.autumn.audited.OperationAuditedLog;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.data.PageQueryBuilder;
import com.example.autumn.redis.article.ArticleInput;
import com.example.autumn.redis.article.ArticleOutput;
import com.example.autumn.redis.article.ArticleSelectDto;
import com.example.autumn.redis.base.AbstractSpEditApplicationService;
import com.example.autumn.redis.base.BaseIdDto;
import com.example.autumn.redis.entities.Article;
import com.example.autumn.redis.repositories.ArticleRepository;
import com.example.autumn.redis.service.ArticleService;
import com.example.autumn.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yanlianglong
 * @Description:文章管理服务实现
 * @date 2020-6-10
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
    private OperationAuditedLog operationAuditedLog;
    @Autowired
    private RedisService<ArticleOutput> redisService;

    @Override
    public String getModuleName() {
        return "文章管理";
    }

    @Override
    protected void queryByOrder(EntityQueryWrapper<Article> query) {
        query.lambda().orderByDescending(Article::getId);
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
        article.setSendStatus(0);
        return article;
    }

    /**
     * @param input
     * @Description: 删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleOutput deleteById(BaseIdDto input) {
        Article article = this.getEntity(input.getId());
        this.getRepository().update(article);
        operationAuditedLog.addLog(this.getModuleName(), "删除文章", article);
        return AutoMapUtils.map(article, ArticleOutput.class);
    }

    /***
     * @Description: 分页查询列表
     * @param input
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResult<ArticleOutput> queryListPage(ArticleSelectDto input) {
        List<ArticleOutput> list = redisService.reverseRangeWithScores("article12",input.getCurrentPage(), input.getPageSize());
        if(list != null){
            System.out.println("list.size():"+list.size());
        }
        PageQueryBuilder<Article> query = new PageQueryBuilder<>(this.getQueryEntityClass());
//        if(list!=null&&list.size()>0){
//            return null;
//        }
        this.generateQueryListColumn(query.getQuery());
        this.systemByCriteria(query.getQuery());
        this.queryByOrder(query.getQuery());
        query.page(input.getCurrentPage(), input.getPageSize());
        //TODO 搜索条件
        PageResult<ArticleOutput> result = query.toPageResult(getQueryRepository(), this.getOutputItemClass(), this::itemConvertHandle);
        redisService.zSetListOrderById("article12",result.getItems());
        return result;
    }
}

