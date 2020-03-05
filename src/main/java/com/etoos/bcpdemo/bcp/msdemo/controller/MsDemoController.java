package com.etoos.bcpdemo.bcp.msdemo.controller;

import com.etoos.bcpdemo.bcp.msdemo.entity.MsDemoEntity;
import com.etoos.bcpdemo.bcp.msdemo.service.MsDemoService;
import com.etoos.bcpdemo.common.aspect.DatabaseRouter;
import com.etoos.bcpdemo.common.constant.DataSourceDirection;
import com.etoos.bcpdemo.common.model.CommonModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms")
@Slf4j
public class MsDemoController {

    @Autowired
    MsDemoService msDemoService;

    @DatabaseRouter(DataSourceDirection.POSTGRES_MASTER)
    @PostMapping("/create")
    public CommonModel createMsDemo(@RequestBody MsDemoEntity msDemoEntity) {
        log.info("{}", msDemoEntity);
        return msDemoService.createMsDemo(msDemoEntity);
    }
}
