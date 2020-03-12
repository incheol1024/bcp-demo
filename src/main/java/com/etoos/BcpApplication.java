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

        System.out.println(System.getProperty("log4jdbc.log4j2.properties.file"));
        SpringApplication.run(BcpApplication.class, args);
    }

}
