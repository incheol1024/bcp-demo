package com.etoos.bcpdemo.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.bcp")
@Getter
@Setter
public class ApplicationProperties {

    String name;

    int id;


}
