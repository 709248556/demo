package com.example.autumn.framework.test;

/**
 * @description:
 * @author: yanlianglong
 * @create: 2020-06-09 15:18
 **/
public class test {
    public static void main(String[] args) {
        People people  = new People();
        people.hello(Hello::hello);
        people.hello(Hello::nothing);
    }
}
