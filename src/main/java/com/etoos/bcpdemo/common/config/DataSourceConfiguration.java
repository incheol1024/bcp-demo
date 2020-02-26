package com.etoos.bcpdemo.common.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfiguration {


    @Bean("dataSourceForPostgresJpaMaster")
    @ConfigurationProperties(prefix = "datasource.postgres.jpa.master")
    public DataSource dataSourceForPostgresJpaMaster() {
        return new HikariDataSource();
    }

    @Bean("dataSourceForPostgresJpaSlaveOne")
    @ConfigurationProperties(prefix = "datasource.postgres.jpa.slave-one")
    public DataSource dataSourceForPostgresJpaSlaveOne() {
        return new HikariDataSource();
    }

    @Bean("dataSourceForPostgresMybatisMaster")
    @ConfigurationProperties(prefix = "datasource.postgres.mybatis.mater")
    public DataSource dataSourceForPostgresMybatisMaster() {
        return new HikariDataSource();
    }

    @Bean("dataSourceForPostgresMybatisSlaveOne")
    @ConfigurationProperties(prefix = "datasource.postgres.mybatis.slave-one")
    public DataSource dataSourceForPostgresMybatisSlaveOne() {
        return new HikariDataSource();
    }

    @Bean("routingDataSourceForPostgresJpa")
    public DataSource routingDataSourceForPostgresJpa(@Qualifier("dataSourceForPostgresJpaMaster") DataSource masterDataSource
            , @Qualifier("dataSourceForPostgresJpaSlaveOne") DataSource slaveDataSource) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", masterDataSource);
        dataSourceMap.put("read", slaveDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }

    @Primary
    @Bean
    public DataSource dataSourceForPostgresJpaMain(
            @Qualifier("routingDataSourceForPostgresJpa") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }


    @Slf4j
    static class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
        @Override
        protected Object determineCurrentLookupKey() {
            String dataSourceType =
                    TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "read" : "write";
            return dataSourceType;
        }
    }


}
