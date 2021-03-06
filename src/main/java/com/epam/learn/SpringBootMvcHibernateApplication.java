package com.epam.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@EnableScheduling
public class SpringBootMvcHibernateApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringBootMvcHibernateApplication.class, args);
    }

}
