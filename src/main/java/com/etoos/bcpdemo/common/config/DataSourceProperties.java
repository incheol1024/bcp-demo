package com.etoos.bcpdemo.common.config;

import lombok.Data;
import org.hibernate.cfg.PropertyHolder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "application.datasource")
@Data
public class DataSourceProperties {

    private DataSourcePropertyHolder postgreHolder = new DataSourcePropertyHolder();

    private DataSourcePropertyHolder msHolder = new DataSourcePropertyHolder();


    @Data
    public static class DataSourcePropertyHolder{

        private String driverClassName;

        private String url;

        private String userName;

        private String password;

    }


}
