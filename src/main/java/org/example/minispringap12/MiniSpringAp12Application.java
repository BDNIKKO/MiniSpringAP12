package org.example.minispringap12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MiniSpringAp12Application {

    public static void main(String[] args) {
        SpringApplication.run(MiniSpringAp12Application.class, args);
    }
}
