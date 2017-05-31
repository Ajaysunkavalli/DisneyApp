package com.disneyapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Bootstraps the Spring Boot com.disney.studios.Application
 *
 * Created by fredjean on 9/21/15.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages="com.disneyapi.repositories")
@ComponentScan(basePackages="com.disneyapi")
public class Application {
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }

}
