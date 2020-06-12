package com.example.autumn.framework.test;

public class People {
    public void hello(Functional functional){
        if(functional != null){
//            System.out.println(functional.apply("FunctionalInterface"));
            functional.apply("FunctionalInterface");
        }
    }
}
