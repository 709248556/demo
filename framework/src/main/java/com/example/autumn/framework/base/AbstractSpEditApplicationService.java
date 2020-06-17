package com.example.autumn.framework.base;

import com.autumn.application.service.AbstractEditApplicationService;
import com.autumn.cloud.uid.UidGenerator;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.DefaultPageResult;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.example.autumn.framework.test.PropertyFunc;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
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
    protected <T> String getKey(PropertyFunc<T, ?> func,Object value){
        try {
            // 通过获取对象方法，判断是否存在该方法
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            System.out.println("getSignature(method):"+getSignature(method));
            // 利用jdk的SerializedLambda 解析方法引用
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(func);
            String getter = serializedLambda.getImplMethodName();
            return resolveFieldName(getter)+value;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
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
    public static <T> String getFieldName(PropertyFunc<T, ?> func,Object value) {
        try {
            // 通过获取对象方法，判断是否存在该方法
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            // 利用jdk的SerializedLambda 解析方法引用
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(func);
            String getter = serializedLambda.getImplMethodName();
            return resolveFieldName(getter);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
    private static String resolveFieldName(String getMethodName) {
        if (getMethodName.startsWith("get")) {
            getMethodName = getMethodName.substring(3);
        } else if (getMethodName.startsWith("is")) {
            getMethodName = getMethodName.substring(2);
        }
        // 小写第一个字母
        return firstToLowerCase(getMethodName);
    }

    private static String firstToLowerCase(String param) {
        if (StringUtils.isBlank(param)) {
            return "";
        }
        return param.substring(0, 1).toLowerCase() + param.substring(1);
    }
    /**
     * 获得方法签名。
     *
     * 格式：returnType#方法名:参数名1,参数名2,参数名3
     * 例如：void#checkPackageAccess:java.lang.ClassLoader,boolean
     *
     * @param method 方法
     * @return 方法签名
     */
    protected String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        // 返回类型
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            sb.append(returnType.getName()).append('#');
        }
        // 方法名
        sb.append(method.getName());
        // 方法参数
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            if (i == 0) {
                sb.append(':');
            } else {
                sb.append(',');
            }
            sb.append(parameters[i].getName());
        }
        return sb.toString();
    }
}
