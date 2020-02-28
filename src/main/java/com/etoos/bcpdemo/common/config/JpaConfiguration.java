package com.etoos.bcpdemo.common.config;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.entity.DemoMsEntity;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import com.etoos.bcpdemo.bcp.demo.repository.mapper.DemoMapper;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Configuration
public class JpaConfiguration {

    @Configuration
    @EnableJpaRepositories(
            entityManagerFactoryRef = "entityManagerFactoryBeanPostgres"
            , transactionManagerRef = "jpaTransactionManager"
            , basePackageClasses = DemoRepository.class)
    static class JpaPostgresConfigurations {

    }

    @Configuration
    @EnableJpaRepositories(
            entityManagerFactoryRef = "entityManagerFactoryBeanMsSql"
            , transactionManagerRef = "transactionManagerMsSqlJpa"
            , basePackageClasses = DemoMapper.class) // 임시
    static class JpaMsSqlConfigurations {

    }

    @Primary
    @Bean("entityManagerFactoryBeanPostgres")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanPostgres(EntityManagerFactoryBuilder builder
            , @Qualifier("dataSourcePostgresMain") DataSource dataSource) {
        Map<String, String> map = new HashMap<>();
        map.put("hibernate.hbm2ddl.auto", "create");
        map.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        map.put("hibernate.show_sql", "true");
        map.put("hibernate.use_jdbc_metadata_defaults", "false");

        return builder.dataSource(dataSource)
                .packages(DemoEntity.class)
//                .persistenceUnit("postgres")
                .properties(map)
                .build();
    }

    @Bean("entityManagerFactoryBeanMsSql")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanMsSql(EntityManagerFactoryBuilder builder
            , @Qualifier("dataSourceMsSqlMain") DataSource dataSource) {
        Map<String, String> map = new HashMap<>();
        map.put("hibernate.hbm2ddl.auto", "create");
        map.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        map.put("hibernate.show_sql", "true");

        return builder.dataSource(dataSource)
                .packages(DemoMapper.class) // 임시
//                .persistenceUnit("msSql")
                .properties(map)
                .build();
    }


    @Primary
    @Bean("transactionManagerPostgresJpa")
    public JpaTransactionManager transactionManagerPostgresJpa(@Qualifier("entityManagerFactoryBeanPostgres") EntityManagerFactory managerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(managerFactory);
        return jpaTransactionManager;
    }

    @Bean("transactionManagerMsSqlJpa")
    public JpaTransactionManager transactionManagerMsSqlJpa(@Qualifier("entityManagerFactoryBeanMsSql") EntityManagerFactory managerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(managerFactory);
        return jpaTransactionManager;
    }



}
