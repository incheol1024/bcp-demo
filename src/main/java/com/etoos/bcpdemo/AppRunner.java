package com.etoos.bcpdemo;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;

@Component
public class AppRunner implements ApplicationRunner {


    @Autowired
    StringEncryptor stringEncryptor;

    @Autowired
    @Qualifier("dataSourceForPostgresJpaMaster")
    DataSource dataSource;

    @Autowired
    @Qualifier("dataSourceForPostgresJpaMain")
    DataSource dataSourceForJpaSlaveOne;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
        System.out.println(metaData);
        System.out.println(metaData.getDatabaseProductName());
        System.out.println(metaData.getUserName());
        System.out.println(metaData.getURL());
        System.out.println(dataSource);

        System.out.println("==============");
        DatabaseMetaData metaData1 = dataSourceForJpaSlaveOne.getConnection().getMetaData();
        System.out.println(metaData1);
        System.out.println(metaData1.getDatabaseProductName());
        System.out.println(metaData1.getUserName());
        System.out.println(metaData1.getURL());
        System.out.println(dataSourceForJpaSlaveOne);

    }
}
