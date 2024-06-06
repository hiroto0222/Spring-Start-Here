package main;

import beans.Parrot;
import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Parrot p1 = context.getBean(Parrot.class);
        System.out.println(p1);
        System.out.println(p1.getName());

//        Parrot p2 = context.getBean("Tom", Parrot.class);
//        System.out.println(p2.getName());
//
//        String s = context.getBean(String.class);
//        System.out.println(s);
//
//        Integer n = context.getBean(Integer.class);
//        System.out.println(n);
    }
}
