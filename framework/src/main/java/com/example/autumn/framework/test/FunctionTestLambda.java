package com.example.autumn.framework.test;

import java.util.Objects;
import java.util.function.Function;

public class FunctionTestLambda<In, Out> {
    private Function<In, Out> processor = in -> {
        return (Out) new String("apply:" + in);
    };

    public static void main(String[] args) {
        FunctionTestLambda<String, String> functionTest = new FunctionTestLambda();
        System.out.println(functionTest.processor.apply("hello~!"));
    }
}
