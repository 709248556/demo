package com.example.autumn.redis.base;

import com.autumn.application.service.AbstractEditApplicationService;
import com.autumn.cloud.uid.UidGenerator;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.DefaultPageResult;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;

/**
 * @param <TEntity>
 * @param <TRepository>
 * @param <TQueryEntity>
 * @param <TQueryRepository>
 * @param <TAddInput>
 * @param <TUpdateInput>
 * @param <TOutputItem>
 * @param <TOutputDetails>
 */
@Log4j2
public abstract class AbstractSpEditApplicationService<
        TEntity extends BaseEntity,
        TRepository extends EntityRepository<TEntity, Long>,
        TQueryEntity extends BaseEntity,
        TQueryRepository extends EntityRepository<TQueryEntity, Long>,
        TAddInput,
        TUpdateInput extends Entity<Long>,
        TOutputItem,
        TOutputDetails> extends AbstractEditApplicationService<Long, TEntity, TRepository, TQueryEntity, TQueryRepository, TAddInput, TUpdateInput, TOutputItem, TOutputDetails> {

//    @Override
//    public SpAdminSession getSession() {
//        return (SpAdminSession) super.getSession();
//    }

    @Override
    protected void systemByCriteria(EntityQueryWrapper<TQueryEntity> wrapper) {
        super.systemByCriteria(wrapper);
//        wrapper.lambda().where().eq(TQueryEntity::getHotelId, this.getSession().getOrganizeId());
    }

    @Override
    protected void systemByEntityCriteria(EntityQueryWrapper<TEntity> wrapper) {
        // (a>1 and b>2) or (a2>3 and a4 <4) or u > 8
        /*
        EntityQueryWrapper<TEntity> query = new EntityQueryWrapper<>(this.getEntityClass());
        query.lambda().where()
                .andNested(q -> q.gt(TEntity::getHotelId, 2).eq(TEntity::getId, 4))
                .orNested(q -> q.gt(TEntity::getHotelId, 2).eq(TEntity::getId, 4))
                .orGt(TEntity::getHotelId,4);
        // select max(a) from t where t.id = 7
        query.lambda().select().max(TEntity::getHotelId)
                .of().where().eq(TEntity::getId,7);
        // select id,name from t group by id,name
        query.lambda().select()
                .column(TEntity::getId)
                .column(TEntity::getHotelId)
                .of()
                .groupBy(TEntity::getId)
                .groupBy(TEntity::getHotelId)
                .where().eq(TEntity::getId,7);
        // select id,max(b) from t where ... group by id
        query.lambda().select()
                .column(TEntity::getId)
                .max(TEntity::getHotelId)
                .of()
                .groupBy(TEntity::getId)
                .where().eq(TEntity::getId,7);

*/


        super.systemByEntityCriteria(wrapper);
//        wrapper.lambda().where().eq(TEntity::getHotelId, this.getSession().getOrganizeId());
    }

    @Override
    protected TEntity addBefore(TAddInput tAddInput, EntityQueryWrapper<TEntity> query) {
        TEntity result = super.addBefore(tAddInput, query);
//        result.setHotelId(this.getSession().getOrganizeId());
        result.setCreatedAt(new Date());
        result.setUpdatedAt(new Date());
        return result;
    }

    @Override
    protected void updateBefore(TUpdateInput tUpdateInput, TEntity entity, EntityQueryWrapper<TEntity> wrapper) {
        getEntity(entity.getId());
        super.updateBefore(tUpdateInput, entity, wrapper);
//        entity.setHotelId(this.getSession().getOrganizeId());
        entity.setUpdatedAt(new Date());
        System.out.println("getMethodSign:"+this.getMethodSign());
    }

    @Autowired
    private UidGenerator uidGeneratorl;

    /**
     * 获取置顶参数
     *
     * @return
     */
    public Long getTop() {
        return uidGeneratorl.newUID().getUid();
    }

    public TEntity getEntity(Long id) {
        EntityQueryWrapper<TEntity> wrapper = this.createEntityWrapperById(id);
        this.systemByEntityCriteria(wrapper);
        TEntity entity = this.getRepository().selectForFirst(wrapper);
        if (entity == null) {
            log.error("{}，对象不存在",id);
            throw ExceptionUtils.throwUserFriendlyException("对象不存在");
        }
        return entity;
    }

    public TQueryEntity getQueryEntity(Long id) {
        EntityQueryWrapper<TQueryEntity> wrapper = this.createQueryWrapperById(id);
        this.systemByCriteria(wrapper);
        TQueryEntity tQueryEntity = this.getQueryRepository().selectForFirst(wrapper);
        if (tQueryEntity == null) {
            log.error("{}，对象不存在",id);
            throw ExceptionUtils.throwUserFriendlyException("对象不存在");
        }
        return tQueryEntity;
    }

    public DefaultPageResult<TOutputItem> emptyPageResult() {
        DefaultPageResult<TOutputItem> page = new DefaultPageResult<TOutputItem>(0, 10, 0);
        page.setItems(new ArrayList<>());
        return page;
    }
    protected String getMethodSign() {
        String result = "";
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            if (stackTrace[i].getMethodName().equals("getMethodSign")) {
                result = stackTrace[i + 1].getClassName() + "." + stackTrace[i + 1].getMethodName();
                break;
            }
        }
        return result;
    }
}
