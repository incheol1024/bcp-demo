package com.etoos.bcpdemo;

import com.etoos.bcpdemo.common.config.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    DataSourceProperties dataSourceProperties;

    @Autowired
    @Qualifier("datasource-postgre-jpa")
    DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        System.out.println(dataSourceProperties.toString());
        System.out.println(dataSourceProperties.getPostgreHolder());



    }
}
