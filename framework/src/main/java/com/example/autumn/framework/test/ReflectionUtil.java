package com.example.autumn.framework.test;

import org.apache.commons.lang.StringUtils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {
    /***
    * @Description: 获取对应的字段名
    * @Param: [func]
    */
    public static <T> String getFieldName(PropertyFunc<T, ?> func) {
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

    /***
    * @Description: 获取对应的字段值
    * @Param: [object, func]
    */
    public static <T> Object getFieldValue(Object object,PropertyFunc<T, ?> func) {
        return getValue(object,getFieldName(func));
    }
    private static Object getValue(Object object, String name){
        Object value = null;
        try {
            Class<?> clz = object.getClass();
            Field nameField = clz.getDeclaredField(name);
            nameField.setAccessible(true);
            value = nameField.get(object);
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }
}
