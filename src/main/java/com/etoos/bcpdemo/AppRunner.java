package com.etoos.bcpdemo;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {


    @Autowired
    StringEncryptor stringEncryptor;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String encrypt = stringEncryptor.encrypt("sa");
        String decrypt = stringEncryptor.decrypt(encrypt);


        System.out.println(encrypt);
        System.out.println(decrypt);


    }
}
