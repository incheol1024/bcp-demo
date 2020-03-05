package com.etoos.bcpdemo.bcp.demo;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import com.etoos.bcpdemo.bcp.demo.service.DemoService;
import com.etoos.bcpdemo.common.model.CommonModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AopSpeedCheck {

    @Autowired
    DemoService demoService;

    int loopCount;

    @BeforeEach
    void setUp() {
        loopCount = 100000;
    }


    // aop사용시 5m 34s 540s
    // aop 사용안할대 7m 51s 159ms
    @Test
    void runAop() {
        Instant instant = Instant.now();
        for (int i = 1; i <= 100000; i++) {
            DemoVo demoVo = new DemoVo();
            demoVo.setId(i);
            demoVo.setName("incheol");
            demoService.createEntity(demoVo);
        }
        System.out.println(Duration.between(instant, Instant.now()));


    }

    @Autowired
    DemoRepository demoRepository;

    @Test
    void repoTest() {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setId(1);
        demoEntity.setName("aaa");


        DemoVo demoVo = new DemoVo();
        demoVo.setId(1);
        demoVo.setName("aaaaa");

        CommonModel entity = demoService.createEntity(demoVo);
        System.out.println(entity);


    }

    @Test
    void repoFindTest() {
        Optional<DemoEntity> byId = demoRepository.findById(106589L);
        DemoEntity demoEntity = byId.get();
        System.out.println(demoEntity);
    }
}
