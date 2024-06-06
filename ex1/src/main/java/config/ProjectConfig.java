package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "beans")
public class ProjectConfig {
}

/*
Using @Bean to define beans
 */
//public class ProjectConfig {
//    @Bean(name = "Koko") // explicitly name bean as "Koko"
//    Parrot parrot1() {
//        var p = new Parrot();
//        p.setName("Koko");
//        return p;
//    }
//
//    @Bean(name = "Tom")
//    Parrot parrot2() {
//        var p = new Parrot();
//        p.setName("Tom");
//        return p;
//    }
//
//    @Bean
//    String hello() {
//        return "Hello";
//    }
//
//    @Bean
//    Integer ten() {
//        return 10;
//    }
//}
