package com.wuping.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wupingping on 16/6/12.
 */
public class SpringTest {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");//读取bean.xml中的内容
        Person p = ctx.getBean("person",Person.class);//创建bean的引用对象
        System.out.println(p.getName());
    }
}
