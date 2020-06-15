package com.example.autumn.redis.test;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.AbstractWrapper;
import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;
import com.autumn.mybatis.wrapper.Wrapper;
import com.autumn.mybatis.wrapper.clauses.AbstractCriteriaClause;
import com.autumn.mybatis.wrapper.commands.CriteriaSection;
import com.autumn.mybatis.wrapper.commands.impl.CriteriaBeanImpl;
import com.autumn.mybatis.wrapper.expressions.AbstractCriteriaExpression;
import com.autumn.mybatis.wrapper.expressions.AbstractExpression;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * 抽象条件
 *
 * @param <TChildren> 子级类型
 * @param <TNameFunc> 名称函数类型
 * @author 老码农 2019-06-11 09:02:09
 */
abstract class Absia<TChildren, TNameFunc> {


    protected Function<TNameFunc, ColumnExpression> columnExpressionFunction;

    /**
     * 实例化
     *
     * @param entityTable              表
     * @param columnExpressionFunction 列表达式生成
     */
    protected Absia(EntityTable entityTable, Function<TNameFunc, ColumnExpression> columnExpressionFunction) {
        this.columnExpressionFunction = columnExpressionFunction;
    }

    /**
     * 创建列表达式
     *
     * @param name 名称或列函数
     * @return
     */
    protected ColumnExpression createColumnExpression(TNameFunc name) {
        return this.columnExpressionFunction.apply(name);
    }

    /**
     * 返回自身 this
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    protected TChildren returnThis() {
        return (TChildren) this;
    }


    /**
     * 条件
     *
     * @param logicalOperater 逻辑运算符
     * @param expression      条件表达式
     * @return
     */
    public abstract TChildren criteria(LogicalOperatorEnum logicalOperater, AbstractCriteriaExpression expression);

    /**
     * 使用 And 创建条件
     *
     * @param expression 条件表达式
     * @return
     */
    public TChildren criteria(AbstractCriteriaExpression expression) {
        return this.criteria(LogicalOperatorEnum.AND, expression);
    }



    /**
     * 条件
     *
     * @param operater         逻辑运算符
     * @param criteriaOperator 条件运算符
     * @param name             名称或列函数
     * @param value            值
     * @return
     */
    public TChildren criteria(LogicalOperatorEnum operater, CriteriaOperatorEnum criteriaOperator, TNameFunc name,
                              Object value) {
        ExceptionUtils.checkNotNull(value, "value");
        ExceptionUtils.checkNotNull(criteriaOperator, "criteriaOperator");
        ColumnExpression column = this.createColumnExpression(name);
        AbstractCriteriaExpression exp = AbstractExpression.binary(criteriaOperator, column, null);
        return criteria(operater, exp);
    }

    /**
     * And 条件表达式
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren criteria(CriteriaOperatorEnum criteriaOperator, TNameFunc name, Object value) {
        return this.criteria(LogicalOperatorEnum.AND, criteriaOperator, name, value);
    }

    /**
     * Or 条件表达式
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orCriteria(CriteriaOperatorEnum criteriaOperator, TNameFunc name, Object value) {
        return this.criteria(LogicalOperatorEnum.OR, criteriaOperator, name, value);
    }

    /**
     * 复制条件
     * <p>
     * 必须由相同的 EntityClass 生成的  Wrapper
     * </p>
     *
     * @param wrapper 包装器
     * @return
     */
    public TChildren copyCriteria(Wrapper wrapper) {
        ExceptionUtils.checkNotNull(wrapper, "wrapper");
        if (wrapper instanceof AbstractWrapper) {
            AbstractWrapper baseWrapper = (AbstractWrapper) wrapper;
            if (baseWrapper.getWrapperCommand().getEexpression() != null) {
                return this.criteria(LogicalOperatorEnum.AND, baseWrapper.getWrapperCommand().getEexpression());
            }
            return this.returnThis();
        }
        CriteriaSection section = wrapper.getSection();
        List<CriteriaBeanImpl> criteriaBeans = new ArrayList<>();
        if (section != null && section.getCriterias() != null) {
            for (AbstractCriteriaClause criteria : section.getCriterias()) {
                CriteriaBeanImpl criteriaBean = new CriteriaBeanImpl();
                criteriaBean.setExpression(criteria.getExpression());
                criteriaBean.setLogic(criteria.getLogic());
                criteriaBean.setOp(criteria.getOp());
                criteriaBean.setValue(criteria.getValue());
                criteriaBean.setSecondValue(criteria.getSecondValue());
                criteriaBeans.add(criteriaBean);
            }
        }
        AbstractCriteriaExpression exp = AbstractExpression.parserBean(null, criteriaBeans);
        if (exp != null) {
            return this.criteria(LogicalOperatorEnum.AND, exp);
        }
        return this.returnThis();
    }

    /**
     * eq(=)条件
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren eq(LogicalOperatorEnum operater, TNameFunc name, Object value) {
        return this.criteria(operater, CriteriaOperatorEnum.EQ, name, value);
    }

    /**
     * And eq(=)条件
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren eq(TNameFunc name, Object value) {
        return eq(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or eq(=)条件
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orEq(TNameFunc name, Object value) {
        return eq(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * notEq(<>)不等于条件
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren notEq(LogicalOperatorEnum operater, TNameFunc name, Object value) {
        return criteria(operater, CriteriaOperatorEnum.NOT_EQ, name, value);
    }

    /**
     * And notEq(<>)不等于条件
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren notEq(TNameFunc name, Object value) {
        return notEq(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or notEq(<>)不等于条件
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orNotEq(TNameFunc name, Object value) {
        return notEq(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * 大于(>)
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren gt(LogicalOperatorEnum operater, TNameFunc name, Object value) {
        return criteria(operater, CriteriaOperatorEnum.GT, name, value);
    }

    /**
     * And 大于(>)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren gt(TNameFunc name, Object value) {
        return gt(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or 大于(>)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orGt(TNameFunc name, Object value) {
        return gt(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * 大于或等于(>=)
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren ge(LogicalOperatorEnum operater, TNameFunc name, Object value) {
        return criteria(operater, CriteriaOperatorEnum.GE, name, value);
    }

    /**
     * And 大于或等于(>=)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren ge(TNameFunc name, Object value) {
        return ge(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or 大于或等于(>=)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orGe(TNameFunc name, Object value) {
        return ge(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * 小于(<)
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren lt(LogicalOperatorEnum operater, TNameFunc name, Object value) {
        return criteria(operater, CriteriaOperatorEnum.LT, name, value);
    }

    /**
     * And 小于(<)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren lt(TNameFunc name, Object value) {
        return lt(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or 小于(<)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orLt(TNameFunc name, Object value) {
        return lt(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * 小于或等于(<=)
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren le(LogicalOperatorEnum operater, TNameFunc name, Object value) {
        return criteria(operater, CriteriaOperatorEnum.LE, name, value);
    }

    /**
     * And 小于或等于(<=)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren le(TNameFunc name, Object value) {
        return le(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * field is null
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @return
     */
    public TChildren isNull(LogicalOperatorEnum operater, TNameFunc name) {
        ColumnExpression column = this.createColumnExpression(name);
        return criteria(operater, AbstractExpression.isNull(column));
    }

    /**
     * And is null
     *
     * @param name 属性或列名
     * @return
     */
    public TChildren isNull(TNameFunc name) {
        return isNull(LogicalOperatorEnum.AND, name);
    }

    /**
     * Or is null
     *
     * @param name 属性或列名
     * @return
     */
    public TChildren orIsNull(TNameFunc name) {
        return isNull(LogicalOperatorEnum.OR, name);
    }

    /**
     * field is not null
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @return
     */
    public TChildren isNotNull(LogicalOperatorEnum operater, TNameFunc name) {
        ColumnExpression column = this.createColumnExpression(name);
        return criteria(operater, AbstractExpression.isNotNull(column));
    }

    /**
     * field is not null
     *
     * @param name 属性或列名
     * @return
     */
    public TChildren isNotNull(TNameFunc name) {
        return isNotNull(LogicalOperatorEnum.AND, name);
    }

    /**
     * Or is not null
     *
     * @param name 属性或列名
     * @return
     */
    public TChildren orIsNotNull(TNameFunc name) {
        return isNotNull(LogicalOperatorEnum.OR, name);
    }

    /**
     * Like 包含%value%
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren like(LogicalOperatorEnum operater, TNameFunc name, String value) {
        return criteria(operater, CriteriaOperatorEnum.LIKE, name, value);
    }

    /**
     * And Like 包含%value%
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren like(TNameFunc name, String value) {
        return like(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or Like 包含%value%
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orLike(TNameFunc name, String value) {
        return like(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * 包含(与like等同)
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren contains(LogicalOperatorEnum operater, TNameFunc name, String value) {
        return like(operater, name, value);
    }

    /**
     * And 包含(与like等同)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren contains(TNameFunc name, String value) {
        return contains(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or 包含(与like等同)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orContains(TNameFunc name, String value) {
        return contains(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * Like 左包含 value %
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren leftLike(LogicalOperatorEnum operater, TNameFunc name, String value) {
        return criteria(operater, CriteriaOperatorEnum.LEFT_LIKE, name, value);
    }

    /**
     * and Like 左包含 value %
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren leftLike(TNameFunc name, String value) {
        return leftLike(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or Like 左包含 value %
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orLeftLike(TNameFunc name, String value) {
        return leftLike(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * 开始匹配(与leftLike同效)
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren startsWith(LogicalOperatorEnum operater, TNameFunc name, String value) {
        return leftLike(operater, name, value);
    }

    /**
     * And 开始匹配(与leftLike同效)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren startsWith(TNameFunc name, String value) {
        return startsWith(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or 开始匹配(与leftLike同效)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orSartsWith(TNameFunc name, String value) {
        return startsWith(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * Like 右包含 % value
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren rigthLike(LogicalOperatorEnum operater, TNameFunc name, String value) {
        return criteria(operater, CriteriaOperatorEnum.RIGHT_LIKE, name, value);
    }

    /**
     * and Like 右包含 % value
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren rigthLike(TNameFunc name, String value) {
        return rigthLike(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or Like 右包含 % value
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orRigthLike(TNameFunc name, String value) {
        return rigthLike(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * 结束匹配(与rigthLike同效)
     *
     * @param operater 逻辑运算符
     * @param name     属性或列名
     * @param value    值
     * @return
     */
    public TChildren endWith(LogicalOperatorEnum operater, TNameFunc name, String value) {
        return rigthLike(operater, name, value);
    }

    /**
     * And 结束匹配(与rigthLike同效)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren endWith(TNameFunc name, String value) {
        return endWith(LogicalOperatorEnum.AND, name, value);
    }

    /**
     * Or 结束匹配(与rigthLike同效)
     *
     * @param name  属性或列名
     * @param value 值
     * @return
     */
    public TChildren orEndWith(TNameFunc name, String value) {
        return endWith(LogicalOperatorEnum.OR, name, value);
    }

    /**
     * between 表达式
     *
     * @param operater    逻辑运算符
     * @param name        属性或列名称
     * @param value       值
     * @param secondValue to值
     * @return
     */
    public TChildren between(LogicalOperatorEnum operater, TNameFunc name, Object value, Object secondValue) {
        ExceptionUtils.checkNotNull(value, "value");
        ExceptionUtils.checkNotNull(secondValue, "secondValue");
        ColumnExpression column = this.createColumnExpression(name);
        AbstractCriteriaExpression exp = AbstractExpression.binary(CriteriaOperatorEnum.BETWEEN, column,
                AbstractExpression.between(null,null));
        return criteria(operater, exp);
    }

    /**
     * and between 表达式
     *
     * @param name        属性或列名称
     * @param value       值
     * @param secondValue to值
     * @return
     */
    public TChildren between(TNameFunc name, Object value, Object secondValue) {
        return between(LogicalOperatorEnum.AND, name, value, secondValue);
    }

    /**
     * Or between 表达式
     *
     * @param name        属性或列名称
     * @param value       值
     * @param secondValue to值
     * @return
     */
    public TChildren orBetween(TNameFunc name, Object value, Object secondValue) {
        return between(LogicalOperatorEnum.OR, name, value, secondValue);
    }

    /**
     * 检查值集合
     *
     * @param values
     */
    private List<Object> checkValues(Object... values) {
        ExceptionUtils.checkNotNull(values, "values");
        if (values.length == 0) {
            ExceptionUtils.throwValidationException("至少需要提供一个值以上。");
        }
        List<Object> lst = new ArrayList<>(values.length);
        for (Object value : values) {
            if (value == null) {
                ExceptionUtils.throwValidationException("不能包含 null 的值。");
            }
            lst.add(value);
        }
        return lst;
    }

    /**
     * 检查值集合
     *
     * @param values
     */
    private List<Object> checkValues(Collection<Object> values) {
        ExceptionUtils.checkNotNull(values, "values");
        if (values.size() == 0) {
            ExceptionUtils.throwValidationException("至少需要提供一个值以上。");
        }
        List<Object> items = new ArrayList<>();
        for (Object value : values) {
            if (value == null) {
                ExceptionUtils.throwValidationException("不能包含 null 的值。");
            }
            items.add(value);
        }
        return items;
    }

    /**
     * In 或 Not In 表达式
     *
     * @param criteriaOperator
     * @param logicalOperater
     * @param name
     * @param values
     * @return
     */
    private TChildren inOrNotIn(CriteriaOperatorEnum criteriaOperator, LogicalOperatorEnum logicalOperater,
                                TNameFunc name, List<Object> values) {
        ExceptionUtils.checkNotNull(criteriaOperator, "criteriaOperator");
        ColumnExpression column = this.createColumnExpression(name);
        AbstractCriteriaExpression exp = AbstractExpression.binary(criteriaOperator, column,
                AbstractExpression.constant(values));
        return criteria(logicalOperater, exp);
    }

    /**
     * in 表达式
     *
     * @param operater
     * @param name     属性或列名称
     * @param values
     * @return
     */
    public TChildren in(LogicalOperatorEnum operater, TNameFunc name, Collection<Object> values) {
        return this.inOrNotIn(CriteriaOperatorEnum.IN, operater, name, this.checkValues(values));
    }

    /**
     * And in 表达式
     *
     * @param name   属性或列名
     * @param values
     * @return
     */
    public TChildren in(TNameFunc name, Collection<Object> values) {
        return in(LogicalOperatorEnum.AND, name, values);
    }

    /**
     * Or in 表达式
     *
     * @param name   属性或列名
     * @param values
     * @return
     */
    public TChildren orIn(TNameFunc name, Collection<Object> values) {
        return in(LogicalOperatorEnum.OR, name, values);
    }

    /**
     * in 表达式
     *
     * @param operater
     * @param name     属性或列名称
     * @param values
     * @return
     */
    public TChildren in(LogicalOperatorEnum operater, TNameFunc name, Object... values) {
        return this.inOrNotIn(CriteriaOperatorEnum.IN, operater, name, this.checkValues(values));
    }

    /**
     * And in 表达式
     *
     * @param name   属性或列名
     * @param values
     * @return
     */
    public TChildren in(TNameFunc name, Object... values) {
        return in(LogicalOperatorEnum.AND, name, values);
    }

    /**
     * Or in 表达式
     *
     * @param name   属性或列名
     * @param values
     * @return
     */
    public TChildren orIn(TNameFunc name, Object... values) {
        return in(LogicalOperatorEnum.OR, name, values);
    }

    /**
     * not in 表达式
     *
     * @param operater
     * @param name     属性或列名称
     * @param values
     * @return
     */
    public TChildren notIn(LogicalOperatorEnum operater, TNameFunc name, Collection<Object> values) {
        return this.inOrNotIn(CriteriaOperatorEnum.NOT_IN, operater, name, this.checkValues(values));
    }

    /**
     * and not in 表达式
     *
     * @param name   属性或列名
     * @param values
     * @return
     */
    public TChildren notIn(TNameFunc name, Collection<Object> values) {
        return notIn(LogicalOperatorEnum.AND, name, values);
    }

    /**
     * Or not in 表达式
     *
     * @param name   属性或列名
     * @param values
     * @return
     */
    public TChildren orNotIn(TNameFunc name, Collection<Object> values) {
        return notIn(LogicalOperatorEnum.OR, name, values);
    }

    /**
     * not in 表达式
     *
     * @param operater
     * @param name     属性或列名称
     * @param values
     * @return
     */
    public TChildren notIn(LogicalOperatorEnum operater, TNameFunc name, Object... values) {
        return this.inOrNotIn(CriteriaOperatorEnum.NOT_IN, operater, name, this.checkValues(values));
    }

    /**
     * And not in 表达式
     *
     * @param name   属性或列名
     * @param values
     * @return
     */
    public TChildren notIn(TNameFunc name, Object... values) {
        return notIn(LogicalOperatorEnum.AND, name, values);
    }

    /**
     * Or not in 表达式
     *
     * @param name   属性或列名
     * @param values
     * @return
     */
    public TChildren orNotIn(TNameFunc name, Object... values) {
        return notIn(LogicalOperatorEnum.OR, name, values);
    }

    /**
     * In 或 Not In 表达式
     *
     * @param criteriaOperator
     * @param logicalOperater
     * @param name
     * @param sql
     * @return
     */
    private TChildren inOrNotInBySql(CriteriaOperatorEnum criteriaOperator, LogicalOperatorEnum logicalOperater,
                                     TNameFunc name, String sql) {
        ExceptionUtils.checkNotNull(criteriaOperator, "criteriaOperator");
        ColumnExpression column = this.createColumnExpression(name);
        AbstractCriteriaExpression exp = AbstractExpression.binary(criteriaOperator, column,
                AbstractExpression.constant(sql));
        return criteria(logicalOperater, exp);
    }

    /**
     * in Sql表达式
     *
     * @param operater
     * @param name     属性或列名称
     * @param sql
     * @return
     */
    public TChildren inBySql(LogicalOperatorEnum operater, TNameFunc name, String sql) {
        return this.inOrNotInBySql(CriteriaOperatorEnum.IN_SQL, operater, name, sql);
    }

    /**
     * And in Sql表达式
     *
     * @param name 属性或列名称
     * @param sql  sql代码
     * @return
     */
    public TChildren inBySql(TNameFunc name, String sql) {
        return inBySql(LogicalOperatorEnum.AND, name, sql);
    }

    /**
     * Or in Sql表达式
     *
     * @param name 属性或列名称
     * @param sql  sql代码
     * @return
     */
    public TChildren orInBySql(TNameFunc name, String sql) {
        return inBySql(LogicalOperatorEnum.OR, name, sql);
    }

    /**
     * not in 的Sql表达式
     *
     * @param operater
     * @param name     属性或列名称
     * @param sql      sql代码
     * @return
     */
    public TChildren notInBySql(LogicalOperatorEnum operater, TNameFunc name, String sql) {
        return this.inOrNotInBySql(CriteriaOperatorEnum.NOT_IN_SQL, operater, name, sql);
    }

    /**
     * and not in 的Sql表达式
     *
     * @param name 属性或列名称
     * @param sql
     * @return
     */
    public TChildren notInBySql(TNameFunc name, String sql) {
        return notInBySql(LogicalOperatorEnum.AND, name, sql);
    }

    /**
     * or not in 的Sql表达式
     *
     * @param name 属性或列名称
     * @param sql
     * @return
     */
    public TChildren orNotInBySql(TNameFunc name, String sql) {
        return notInBySql(LogicalOperatorEnum.OR, name, sql);
    }

    /**
     * exists 表达式
     *
     * @param operater 逻辑运算符
     * @param sql      sql代码
     * @return
     */
    public TChildren exists(LogicalOperatorEnum operater, String sql) {
        ExceptionUtils.checkNotNullOrBlank(sql, "sql");
        return criteria(operater, AbstractExpression.unary(CriteriaOperatorEnum.EXISTS, sql));
    }

    /**
     * and exists 表达式
     *
     * @param sql sql代码
     * @return
     */
    public TChildren exists(String sql) {
        return this.exists(LogicalOperatorEnum.AND, sql);
    }

    /**
     * or exists 表达式
     *
     * @param sql sql代码
     * @return
     */
    public TChildren orExists(String sql) {
        return this.exists(LogicalOperatorEnum.OR, sql);
    }

    /**
     * not exists 表达式
     *
     * @param operater 逻辑运算符
     * @param sql      sql代码
     * @return
     */
    public TChildren notExists(LogicalOperatorEnum operater, String sql) {
        ExceptionUtils.checkNotNullOrBlank(sql, "sql");
        return criteria(operater, AbstractExpression.unary(CriteriaOperatorEnum.NOT_EXISTS, sql));
    }

    /**
     * and not exists 表达式
     *
     * @param sql sql代码
     * @return
     */
    public TChildren notExists(String sql) {
        return this.notExists(LogicalOperatorEnum.AND, sql);
    }

    /**
     * or not exists 表达式
     *
     * @param sql sql代码
     * @return
     */
    public TChildren orNotExists(String sql) {
        return this.notExists(LogicalOperatorEnum.OR, sql);
    }

}
