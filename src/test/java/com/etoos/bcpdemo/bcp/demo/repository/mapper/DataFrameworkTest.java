package com.etoos.bcpdemo.bcp.demo.repository.mapper;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import com.etoos.bcpdemo.common.aspect.DatabaseRouter;
import com.etoos.bcpdemo.common.constant.DataSourceDirection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.aop.MethodMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.Instant;

@SpringBootTest
@ExtendWith({SpringExtension.class, DataFrameworkTest.TimingExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataFrameworkTest {

    @Autowired
    DemoRepository demoRepository;

    @Autowired
    DemoMapper demoMapper;

    Instant instant;

    @BeforeAll
    static void setUpClass() {

    }

    @BeforeEach
    void setUp() {
        instant = Instant.now();
    }

    @AfterEach
    void after() {
        System.out.println("======================");
        System.out.println("=======" + Duration.between(instant, Instant.now()).toMillis() + "======");
        System.out.println("======================");
    }


    @Test
    @Order(0)
    void test() {
        System.out.println("test");
    }

    @Test
    @Order(1)
    void hibernate_Insert_10000_Time() {
        Instant start = Instant.now();
        for (int i = 1; i <= 10000; i++) {
            DemoEntity demoEntity = new DemoEntity();
//            demoEntity.setId(i);
            demoEntity.setName("incheol");
            demoRepository.save(demoEntity);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis()); //38101 ms
    }


    @Test
    @Order(5)
    void myBatis_Insert_10000_Time() {
        Instant start = Instant.now();
        for (int i = 1; i <= 10000; i++) {
            DemoVo demoVo = new DemoVo();
            demoVo.setId(i);
            demoVo.setName("incheol");
            demoMapper.insertDemo(demoVo);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis()); // 21942, 21582 ms
    }


    @Test
    @Order(2)
    void hibernate_Read_10000_Time() {
        Instant start = Instant.now();
        for (int i = 1; i <= 10000; i++) {
            demoRepository.findById(Long.valueOf(i));
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis()); // 13558, 12258 ms
    }

    @Test
    @Order(6)
    void myBatis_Read_10000_Time() {
        Instant start = Instant.now();
        for (int i = 1; i <= 10000; i++) {
            demoMapper.findById(i);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis()); // 6555, 6538 ms
    }

    @Test
    @Order(3)
    void hibernate_Update_10000_Time() {
        Instant start = Instant.now();
        for (int i = 1; i <= 10000; i++) {
            DemoEntity demoEntity = new DemoEntity();
            demoEntity.setId(i);
            demoEntity.setName("update");
            demoRepository.save(demoEntity);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis()); // 42616 ms
    }


    @Test
    @Order(7)
    void myBatis_Update_10000_Time() {
        Instant start = Instant.now();
        for (int i = 1; i <= 10000; i++) {
            DemoVo demoVo = new DemoVo();
            demoVo.setId(i);
            demoVo.setName("update mb");
            demoMapper.updateDemo(demoVo);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis()); // 23176 ms
    }

    @Test
    @Order(4)
    void hibernate_Delete_10000_Time() {
        Instant start = Instant.now();
        for (int i = 1; i <= 10000; i++) {
            DemoEntity demoEntity = new DemoEntity();
            demoEntity.setId(i);
            demoRepository.delete(demoEntity);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis()); // 42321 ms
    }

    @Test
    @Order(8)
    void myBatis_Delete_10000_Time() {
        Instant start = Instant.now();
        for (int i = 1; i <= 10000; i++) {
            DemoVo demoVo = new DemoVo();
            demoVo.setId(i);
            demoMapper.deleteDemo(1L);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis()); // 22123 ms
    }


    @Slf4j
    public static class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

        @Override
        public void afterTestExecution(ExtensionContext context) throws Exception {
            System.out.println("afterTestExecution");
        }

        @Override
        public void beforeTestExecution(ExtensionContext context) throws Exception {
            System.out.println("beforeTestExecution");
        }
    }

}
