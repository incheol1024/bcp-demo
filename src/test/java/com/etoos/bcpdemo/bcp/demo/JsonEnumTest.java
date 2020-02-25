package com.etoos.bcpdemo.bcp.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@TestPropertySource(locations = {"classpath:/application.properties"})
@ExtendWith(SpringExtension.class)
public class JsonEnumTest {

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void parsingTest() throws JsonProcessingException {
//        String s = objectMapper.writeValueAsString(InfoMessagesEnum.CODE_DEFAULT);
//        System.out.println(s);
    }
}
