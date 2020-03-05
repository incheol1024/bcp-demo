package com.etoos.bcpdemo.common.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Objects;


@Configuration
// application.properties 가 클래스패스에 반드시 존재해야만 ApplicationConfiguration 생성하는 조건을 부여했음.
@ConditionalOnResource(resources = "classpath:application.properties")
@EnableConfigurationProperties({ApplicationProperties.class})
public class ApplicationConfiguration {

    private static final int DEFAULT_ID = 0;

    private static final String DEFAULT_NAME = "default name";

    @Bean
    @Primary
    ApplicationProperties applicationProperties(ApplicationProperties properties) {
        if (properties.getId() != DEFAULT_ID) {
            // do not anything
        } else {
            properties.setId(DEFAULT_ID);
        }

        if (Objects.isNull(properties.getName()) || properties.getName().equals(""))
            properties.setName(DEFAULT_NAME);

        return properties;
    }


    @Bean(name = "encryptorBean")
    public StringEncryptor stringEncryptor(SimpleStringPBEConfig simpleStringPBEConfig) {
        PooledPBEStringEncryptor stringEncryptor = new PooledPBEStringEncryptor();
        stringEncryptor.setConfig(simpleStringPBEConfig);
        return stringEncryptor;
    }


    @Bean
    @ConfigurationProperties("config.encrypt")
    public SimpleStringPBEConfig simpleStringPBEConfig() {
        return new SimpleStringPBEConfig();
    }




}
