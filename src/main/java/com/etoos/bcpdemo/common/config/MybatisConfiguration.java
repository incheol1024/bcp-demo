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
@MapperScan(basePackages = "com.etoos.bcpdemo.bcp")
public class MybatisConfiguration {

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("dataSourcePostgresMain") DataSource dataSource) throws Exception {
        System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());
        System.out.println("=========" + dataSource.getClass() + "============");
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("com.etoos.bcpdemo.bcp.demo.model.vo");
        sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:/*.xml"));
        return sqlSessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean("transactionManagerPostgres")
    public DataSourceTransactionManager transactionManagerForPostgres(@Qualifier("dataSourcePostgresMain") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
