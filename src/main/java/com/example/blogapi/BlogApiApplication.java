package com.example.blogapi;

import com.example.blogapi.model.Person;
import com.example.blogapi.service.serviceImplementation.PersonImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;


@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class BlogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);


    }

}
