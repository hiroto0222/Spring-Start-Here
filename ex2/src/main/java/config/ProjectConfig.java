package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "beans")
public class ProjectConfig {
}

//public class ProjectConfig {
//    @Bean
//    public Parrot parrot() {
//        Parrot p = new Parrot();
//        p.setName("Koko");
//        return p;
//    }
//
//    @Bean
//    public Person person(Parrot parrot) {
//        Person p = new Person();
//        p.setName("Ella");
//        p.setParrot(parrot);
//        return p;
//    }
//}
