package com.etoos.bcpdemo.bcp.demo;

import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void makeJson() throws JsonProcessingException {

        List<DemoVo> list = new ArrayList<>();

        DemoVo demoVo = new DemoVo();
        demoVo.setName("incheol");
        demoVo.setId(1);

        DemoVo demoVo1 = new DemoVo();
        demoVo.setName("incheol");
        demoVo.setId(1);

        list.add(demoVo);
        list.add(demoVo1);

        Object testObj = list;

        String s = objectMapper.writeValueAsString(testObj);
        System.out.println(s);


    }

}
