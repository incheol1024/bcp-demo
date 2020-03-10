package com.etoos.common.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.etoos.common.database.DataSourceType;
import com.etoos.common.database.RoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Order(1)
public class DataSourceConfig {

    @Bean("dataSourceDefault")
    @ConfigurationProperties(prefix = "datasource.postgres.default")
    public DataSource dataSourceDefault() {
        return new HikariDataSource();
    }

    @Bean("dataSourceBcpRead")
    @ConfigurationProperties(prefix = "datasource.postgres.bcp-read")
    public DataSource dataSourceBcpRead() {
        return new HikariDataSource();
    }

    @Bean("dataSourceMember")
    @ConfigurationProperties(prefix = "datasource.mssql.member")
    public DataSource dataSourceMember() {
        return new HikariDataSource();
    }

    @Bean(name="multiRoutingDataSource")
    public DataSource multiRoutingDataSource(
            @Qualifier("dataSourceDefault") DataSource dataSourceDefault
            , @Qualifier("dataSourceBcpRead") DataSource dataSourceBcpRead
            , @Qualifier("dataSourceMember") DataSource dataSourceMember
            ) {
        RoutingDataSource multiRoutingDataSource = new RoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.DEFAULT, dataSourceDefault);
        dataSourceMap.put(DataSourceType.BCPREAD, dataSourceBcpRead);
        dataSourceMap.put(DataSourceType.MEMBER, dataSourceMember);
        multiRoutingDataSource.setTargetDataSources(dataSourceMap);
        multiRoutingDataSource.setDefaultTargetDataSource(dataSourceDefault);
        multiRoutingDataSource.afterPropertiesSet();

        return multiRoutingDataSource;

    }

}
