package com.example.autumn.framework.test;
import java.util.function.Function;

public class FunctionTest<In, Out> {

    private Function<In, Out> processor = new Function<In, Out>() {
        @Override
        public Out apply(In in) {
            return (Out) new String("apply:" + in);

        }
    };

    public static void main(String[] args) {
        FunctionTest<String, String> functionTest = new FunctionTest();
        System.out.println(functionTest.processor.apply("hello~!"));
    }
}
