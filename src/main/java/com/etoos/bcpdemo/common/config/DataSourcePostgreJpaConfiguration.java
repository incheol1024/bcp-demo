package com.etoos.bcpdemo.common.config;

import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.etoos.bcpdemo.common.config.DataSourceProperties.DataSourcePropertyHolder;

@Configuration
@EnableConfigurationProperties({DataSourceProperties.class })
/*
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerPostgre"
        , transactionManagerRef = "transactionManagerPostgre"
        , basePackageClasses = DemoRepository.class)
*/
public class DataSourcePostgreJpaConfiguration {

    @Configuration
    @EnableJpaRepositories(
            entityManagerFactoryRef = "entityManagerPostgre"
            , transactionManagerRef = "transactionManagerPostgre"
            , basePackageClasses = DemoRepository.class)
    static class DataSourcePostgresJpaConfiguration {

    }



    @Primary
    @Bean("datasource-postgre-jpa")
    public DataSource dataSourcePostgre(DataSourceProperties datas) {
        DataSourcePropertyHolder postgreHolder = datas.getPostgreHolder();

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(postgreHolder.getDriverClassName());
        hikariDataSource.setJdbcUrl(postgreHolder.getUrl());
        hikariDataSource.setUsername(postgreHolder.getUserName());
        hikariDataSource.setPassword(postgreHolder.getPassword());
        return hikariDataSource;
//        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean("entityManagerPostgre")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder
            , @Qualifier("datasource-postgre-jpa") DataSource dataSource) {

        return builder.dataSource(dataSource)
                .packages("com.etoos.bcpdemo.bcp")
                .persistenceUnit("postgre")
                .properties(additionalJpaProperties())
                .build();

    }




// 임시 샘플 코드
    Map<String, ?> additionalJpaProperties() {
        Map<String, String> map = new HashMap<>();

        map.put("hibernate.hbm2ddl.auto", "create");
        map.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        map.put("hibernate.show_sql", "true");

        return map;
    }


    @Primary
    @Bean("transactionManagerPostgre")
    public JpaTransactionManager jpaTransactionManager(@Qualifier("entityManagerPostgre") EntityManagerFactory managerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(managerFactory);
        return jpaTransactionManager;
    }


}
