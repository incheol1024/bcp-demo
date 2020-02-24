package com.etoos.bcpdemo.common.config;

import com.etoos.bcpdemo.BcpDemoApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Objects;


@Configuration
@ConditionalOnResource(resources = "classpath:application.properties")
// application.properties 가 클래스패스에 반드시 존재해야만 ApplicationConfiguration 생성하는 조건을 부여했음.
@EnableConfigurationProperties({ApplicationProperties.class})
public class ApplicationConfiguration {

    private static final int DEFAULT_ID = 0;

    private static final String DEFAULT_NAME = "default name";


    @Bean
    @Primary
    ApplicationProperties applicationProperties(ApplicationProperties properties) {
        if (properties.getId() != DEFAULT_ID) {
            // do not anything
        } else {
            properties.setId(DEFAULT_ID);
        }

        if (Objects.isNull(properties.getName()) || properties.getName().equals(""))
            properties.setName(DEFAULT_NAME);

        return properties;
    }


/*
    @Bean
    @ConditionalOnSingleCandidate(type = "DataSource")
    @Qualifier("postreSqlDataSource")
    DataSource postgreSqlDataSource(DataSourcePostgreSqlProperties postgreSqlProperties) {
        HikariDataSource hikariDataSource = DataSourceBuilder.create().type(HikariDataSource.class)
                .url(postgreSqlProperties.getUrl())
                .username(postgreSqlProperties.getUserName())
                .password(postgreSqlProperties.getPassword())
                .driverClassName("org.h2.Driver")
                .build();

        hikariDataSource.setConnectionTimeout(postgreSqlProperties.getConnectionTimeOut());
        return hikariDataSource;
    }


    @Bean
    @ConditionalOnSingleCandidate(type = "DataSource")
    @Qualifier("postreSqlDataSource")
    DataSource msSqlDataSource(DataSourceMsSqlProperties msSqlProperties) {
        HikariDataSource hikariDataSource = DataSourceBuilder.create().type(HikariDataSource.class)
                .url(msSqlProperties.getUrl())
                .username(msSqlProperties.getUserName())
                .password(msSqlProperties.getPassword())
                .driverClassName("org.h2.Driver")
                .build();

//        hikariDataSource.setConnectionTimeout(msSqlProperties.getConnectionTimeOut());
        return hikariDataSource;
    }
*/




}
