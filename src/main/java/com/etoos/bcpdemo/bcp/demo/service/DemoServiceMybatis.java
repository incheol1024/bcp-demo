package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.mapper.DemoMapper;
import com.etoos.bcpdemo.bcp.demo.repository.mapper.DemoMsMapper;
import com.etoos.bcpdemo.common.aspect.DatabaseRouter;
import com.etoos.bcpdemo.common.constant.DataSourceDirection;
import com.etoos.bcpdemo.common.exception.CommonException;
import com.etoos.bcpdemo.common.model.CommonModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@Transactional
@DatabaseRouter(DataSourceDirection.POSTGRES_MASTER)
public class DemoServiceMybatis implements DemoService{

    @Autowired
    DemoMapper demoMapper;


    @Override
    @DatabaseRouter(DataSourceDirection.POSTGRES_SLAVE_ONE)
    public CommonModel findDemo(long id) {
        DemoVo demoVo = demoMapper.findById(id);
        return CommonModel.create(demoVo);
    }

    @Override
    public CommonModel createDemo(DemoVo demoVo) {
        demoMapper.insertDemo(demoVo);
        return CommonModel.create(demoVo);
    }

    @Override
    public CommonModel updateDemo(DemoVo demoVo) {
        demoMapper.updateDemo(demoVo);
        return CommonModel.create(demoVo);
    }

    @Override
    public CommonModel deleteDemo(long id) {
        demoMapper.deleteDemo(id);
        return new CommonModel();
    }
}
