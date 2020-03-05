package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.common.aspect.DatabaseRouter;
import com.etoos.bcpdemo.common.constant.DataSourceDirection;
import com.etoos.bcpdemo.common.model.CommonModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DemoBatisServiceTest {

    @Autowired
    DemoBatisService demoBatisService;

    @Test
    public void createDemoPostgres() {
        DemoVo demoVo = new DemoVo();
        demoVo.setId(20);
        demoVo.setName("postgres incheol");

        CommonModel demoPostgres = demoBatisService.createDemoPostgres(demoVo);

    }

    @Test
    public void createDemoMsSql() {
        DemoVo demoVo = new DemoVo();
        demoVo.setId(20);
        demoVo.setName("MS incheol");

        CommonModel demoMsSql = demoBatisService.createDemoMsSql(demoVo);
    }


}