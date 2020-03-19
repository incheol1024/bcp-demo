package com.etoos.common.config;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.navercorp.lucy.security.xss.servletfilter.defender.XssPreventerDefender;
import com.nhncorp.lucy.security.xss.XssSaxFilter;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterConfig;

@Configuration
public class ApplicationConfig {

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
