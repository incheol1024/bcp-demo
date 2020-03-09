package com.etoos.bcpdemo.common.config;

import com.etoos.bcpdemo.common.constant.DataSourceDirection;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfiguration {


    @Bean("dataSourceForPostgresMaster")
    @ConfigurationProperties(prefix = "datasource.postgres.master")
    public DataSource dataSourceForPostgresMaster() {
        return new HikariDataSource();
    }

    @Bean("dataSourceForPostgresSlaveOne")
    @ConfigurationProperties(prefix = "datasource.postgres.slave-one")
    public DataSource dataSourceForPostgresSlaveOne() {
        return new HikariDataSource();
    }

    @Bean("dataSourceForMsSqlMaster")
    @ConfigurationProperties(prefix = "datasource.ms-sql.master")
    public DataSource dataSourceForMsSqlMaster() {
        return new HikariDataSource();
    }

    @Bean("dataSourceForMsSqlSlaveOne")
    @ConfigurationProperties(prefix = "datasource.ms-sql.slave-one")
    public DataSource dataSourceForMsSqlSlaveOne() {
        return new HikariDataSource();
    }


    @Primary
    @Bean("dataSourceRoutingMain")
    public DataSource dataSourceRoutingMain(@Qualifier("dataSourceForPostgresMaster") DataSource dataSourceForPostgresMaster
            , @Qualifier("dataSourceForPostgresSlaveOne") DataSource dataSourceForPostgresSlaveOne
            , @Qualifier("dataSourceForMsSqlMaster") DataSource dataSourceForMsSqlMaster
            , @Qualifier("dataSourceForMsSqlSlaveOne") DataSource dataSourceForMsSqlSlaveOne
    ) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceDirection.POSTGRES_MASTER, dataSourceForPostgresMaster);
        dataSourceMap.put(DataSourceDirection.POSTGRES_SLAVE_ONE, dataSourceForPostgresSlaveOne);
        dataSourceMap.put(DataSourceDirection.MS_SQL_MASTER, dataSourceForMsSqlMaster);
        dataSourceMap.put(DataSourceDirection.MS_SQL_SLAVE_ONE, dataSourceForMsSqlSlaveOne);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(dataSourceForPostgresMaster);
        return routingDataSource;

    }


    @Slf4j
    private static class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
        @Override
        protected Object determineCurrentLookupKey() {
            log.info("datasource key = {} ", DataSourceKeyThreadHolder.getDataSourceKey());
            return DataSourceKeyThreadHolder.getDataSourceKey();
        }
    }


}
