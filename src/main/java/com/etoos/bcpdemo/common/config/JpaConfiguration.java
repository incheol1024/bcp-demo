package com.etoos.bcpdemo.common.config;

import com.etoos.bcpdemo.BcpDemoApplication;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import com.etoos.bcpdemo.bcp.msdemo.entity.MsDemoEntity;
import com.etoos.bcpdemo.bcp.msdemo.repository.MsDemoRepository;
import com.etoos.bcpdemo.common.aspect.DatabaseRouter;
import com.etoos.bcpdemo.common.constant.DataSourceDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JpaConfiguration {

    @Autowired
    private Environment environment;

    private Map<String, String> getHibernateProperties(String dbName) {
        Map<String, String> map = new HashMap<>();
        map.put("hibernate.hbm2ddl.auto", environment.getProperty("jpa.hibernate." + dbName + ".hbm2ddl-auto"));
        map.put("hibernate.dialect", environment.getProperty("jpa.hibernate." + dbName + ".dialect"));
        map.put("hibernate.show_sql", environment.getProperty("jpa.hibernate." + dbName + ".show-sql"));
        map.put("hibernate.use_jdbc_metadata_defaults", environment.getProperty("jpa.hibernate." + dbName + ".use-jdbc-metadata-defaults"));
        return map;
    }

    @Configuration
    @EnableJpaRepositories(
            entityManagerFactoryRef = "entityManagerFactoryBeanPostgres"
            , transactionManagerRef = "transactionManagerPostgresJpa"
            , basePackageClasses = DemoRepository.class)
    class JpaPostgresConfigurations {

        @Primary
        @Bean("entityManagerFactoryBeanPostgres")
        public LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanPostgres(EntityManagerFactoryBuilder builder
                , @Qualifier("dataSourceRoutingMain") DataSource dataSource) {
            Map<String, String> map = getHibernateProperties("postgres");

            return builder.dataSource(dataSource)
                    .packages(BcpDemoApplication.class)
                    .properties(map)
                    .build();
        }

        @Primary
        @Bean("transactionManagerPostgresJpa")
        @Order(1)
        public JpaTransactionManager transactionManagerPostgresJpa(@Qualifier("entityManagerFactoryBeanPostgres") EntityManagerFactory managerFactory) {
            JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
            jpaTransactionManager.setEntityManagerFactory(managerFactory);
            return jpaTransactionManager;
        }

    }

    @Configuration
    @EnableJpaRepositories(
            entityManagerFactoryRef = "entityManagerFactoryBeanMsSql"
            , transactionManagerRef = "transactionManagerMsSqlJpa"
            , basePackageClasses = MsDemoRepository.class) // 임시
    class JpaMsSqlConfigurations {

        @Bean("entityManagerFactoryBeanMsSql")
        public LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanMsSql(EntityManagerFactoryBuilder builder
                , @Qualifier("dataSourceRoutingMain") DataSource dataSource
        ) {
            Map<String, String> map = getHibernateProperties("ms-sql");

            return builder.dataSource(dataSource)
                    .packages(MsDemoEntity.class) // 임시
                    .properties(map)
                    .build();
        }

        @Bean("transactionManagerMsSqlJpa")
        @Order(2)
        public JpaTransactionManager transactionManagerMsSqlJpa(@Qualifier("entityManagerFactoryBeanMsSql") EntityManagerFactory managerFactory) {
            JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
            jpaTransactionManager.setEntityManagerFactory(managerFactory);
            return jpaTransactionManager;
        }

    }


}
