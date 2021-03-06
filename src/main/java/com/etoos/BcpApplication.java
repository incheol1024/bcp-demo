package com.etoos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"org.bitbucket.tek.nik.simplifiedswagger", "com.etoos"})
public class BcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(BcpApplication.class, args);
    }

}
