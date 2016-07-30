package com.baidu.disconf.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DisconfTest {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();
        System.in.read();
    }
}
