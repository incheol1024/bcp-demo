package com.etoos.bcp.sample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.etoos.bcp.sample.service.SampleService;

@SpringBootTest
class BcpApplicationTests {

    @Autowired
    private SampleService sampleService;

    @Test
    void contextLoads() {
    }

    @Test
    public void mybatis_select_test() {
        sampleService.retrieveMybatis();
        sampleService.retrieveMybatisRead();
    }

}
