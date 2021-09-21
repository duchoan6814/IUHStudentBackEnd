package com.iuh.IUHStudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import javax.servlet.Filter;

@SpringBootApplication
public class IuhStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(IuhStudentApplication.class, args);
    }

    @Bean
    public Filter OpenFilter() {
        return new OpenEntityManagerInViewFilter();
    }
}


