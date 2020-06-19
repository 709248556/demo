package com.example.autumn.framework.test;

import com.autumn.mybatis.wrapper.*;
import org.apache.commons.lang.StringUtils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public class KeyBuilder {
    private final static StringBuilder SYMBOL = new StringBuilder(":");
    private String methodSign;
    private StringBuilder key = new StringBuilder();
    public KeyBuilder(String methodSign){
        this.methodSign = methodSign;
    }
    private static <T> String getFieldName(PropertyFunc<T, ?> func,Object value) {
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
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
        return firstToLowerCase(getMethodName);
    }

    private static String firstToLowerCase(String param) {
        if (StringUtils.isBlank(param)) {
            return "";
        }
        return param.substring(0, 1).toLowerCase() + param.substring(1);
    }
    public String getResult(){
        return methodSign+key.toString();
    }

    public <T> KeyBuilder eq(PropertyFunc<T, ?> func,Object value){
            key.append(LogicalOperatorEnum.AND.getOperator()).append(getFieldName(func,value)).append(CriteriaOperatorConstant.EQ).append(value);
            return this;
    }
    public <T> KeyBuilder ne(PropertyFunc<T, ?> func,Object value){
        key.append(LogicalOperatorEnum.AND.getOperator()).append(getFieldName(func,value)).append(CriteriaOperatorConstant.NOT_EQ).append(value);
        return this;
    }
}
