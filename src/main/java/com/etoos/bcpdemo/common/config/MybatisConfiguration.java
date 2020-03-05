package com.etoos.bcpdemo.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MybatisConfiguration {


    @Configuration
    @MapperScan(basePackages = "com.etoos.bcpdemo.bcp"
            , sqlSessionFactoryRef = "sqlSessionFactoryPostgres")
    static class MybatisPostgresConfiguration {

        @Autowired
        ApplicationContext applicationContext;

/*
        @Primary
        @Bean("transactionManager")
        public DataSourceTransactionManager transactionManager(@Qualifier("dataSourceRoutingMain") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
*/


        @Bean
        @Primary
        public SqlSessionFactory sqlSessionFactoryPostgres(
                @Qualifier("dataSourceRoutingMain") DataSource dataSource) throws Exception {
            System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());
            System.out.println("=========" + dataSource.getClass() + "============");
            SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
            sqlSessionFactory.setDataSource(dataSource);
//        sqlSessionFactory.setTypeAliasesPackage("com.etoos.bcpdemo.bcp.demo.model.vo");
            sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:/**/mapper/*.xml"));
            return sqlSessionFactory.getObject();
        }

        @Bean("sqlSessionTemplatePostgres")
        @Primary
        public SqlSessionTemplate sqlSessionTemplatePostgres(SqlSessionFactory sqlSessionFactory) {
            return new SqlSessionTemplate(sqlSessionFactory);
        }

    }

    @Configuration
    @MapperScan(basePackages = "com.etoos.bcpdemo.bcp"
            , sqlSessionFactoryRef = "sqlSessionFactoryMsSql")
    static class MybatisMsSqlConfiguration {

        @Autowired
        ApplicationContext applicationContext;

        @Bean("sqlSessionFactoryMsSql")
        public SqlSessionFactory sqlSessionFactoryMsSql(
                @Qualifier("dataSourceRoutingMain") DataSource dataSource) throws Exception {
            System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());
            System.out.println("=========" + dataSource.getClass() + "============");
            SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
            sqlSessionFactory.setDataSource(dataSource);
//        sqlSessionFactory.setTypeAliasesPackage("com.etoos.bcpdemo.bcp.demo.model.vo");
            sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:/**/mapper/*.xml"));
            return sqlSessionFactory.getObject();
        }

        @Bean("sqlSessionTemplateMsSql")
        public SqlSessionTemplate sqlSessionTemplateMsSql(@Qualifier("sqlSessionFactoryMsSql") SqlSessionFactory sqlSessionFactory) {
            return new SqlSessionTemplate(sqlSessionFactory);
        }

/*
        @Primary
        @Bean("transactionManagerMsSqlMybatis")
        public DataSourceTransactionManager transactionManagerForPostgresMybatis(@Qualifier("dataSourceRoutingMain") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
*/

    }

}
