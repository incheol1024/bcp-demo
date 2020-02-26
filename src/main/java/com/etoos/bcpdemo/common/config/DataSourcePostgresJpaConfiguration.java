package com.etoos.bcpdemo.common.config;

import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
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

@Configuration
@EnableConfigurationProperties({DataSourceProperties.class })
/*
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerPostgre"
        , transactionManagerRef = "transactionManagerPostgre"
        , basePackageClasses = DemoRepository.class)
*/
public class DataSourcePostgresJpaConfiguration {

    @Configuration
    @EnableJpaRepositories(
            entityManagerFactoryRef = "entityManagerPostgres"
            , transactionManagerRef = "transactionManagerPostgres"
            , basePackageClasses = DemoRepository.class)
    static class DataSourcePostgresJpaConfigurations {

    }

    @Primary
    @Bean("entityManagerPostgres")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder
            , @Qualifier("dataSourceForPostgresJpaMaster") DataSource dataSource) {

        return builder.dataSource(dataSource)
                .packages("com.etoos.bcpdemo.bcp")
//                .persistenceUnit("postgre")
                .properties(additionalJpaProperties())
                .build();

    }

// 임시 샘플 코드
    Map<String, ?> additionalJpaProperties() {
        Map<String, String> map = new HashMap<>();

        map.put("hibernate.hbm2ddl.auto", "create");
        map.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        map.put("hibernate.show_sql", "true");
        map.put("hibernate.use_jdbc_metadata_defaults", "false");

        return map;
    }


    @Primary
    @Bean("transactionManagerPostgres")
    public JpaTransactionManager jpaTransactionManager(@Qualifier("entityManagerPostgres") EntityManagerFactory managerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(managerFactory);
        return jpaTransactionManager;
    }


}
