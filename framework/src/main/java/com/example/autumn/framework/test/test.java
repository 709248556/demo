package com.example.autumn.framework.test;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2020-06-09 15:18
 **/
public class test {
    public static void main(String[] args) {
        Dog dog = (String food) -> {System.out.println("-------------"+ food); return food;};
        dog.eat("牛肉");
    }
}
