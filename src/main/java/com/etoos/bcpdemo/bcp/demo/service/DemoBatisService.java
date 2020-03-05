package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.mapper.DemoMapper;
import com.etoos.bcpdemo.bcp.demo.repository.mapper.DemoMsMapper;
import com.etoos.bcpdemo.common.aspect.DatabaseRouter;
import com.etoos.bcpdemo.common.constant.DataSourceDirection;
import com.etoos.bcpdemo.common.exception.CommonException;
import com.etoos.bcpdemo.common.model.CommonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class DemoBatisService {

    @Autowired
    DemoMapper demoMapper;

    @Autowired
    DemoMsMapper demoMsMapper;


    @Transactional(transactionManager = "transactionManagerPostgresMybatis" )
    public CommonModel createDemoPostgres(DemoVo demoVo) {
        demoMapper.insertDemo(demoVo);
        demoMapper.insertDemo(demoVo);
        if(Objects.nonNull(demoVo))
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR);
        return getCommonModel(demoVo);
    }

    @DatabaseRouter(DataSourceDirection.MS_SQL_MASTER)
    public CommonModel createDemoMsSql(DemoVo demoVo) {
        demoMsMapper.insertDemo(demoVo);
        return getCommonModel(demoVo);
    }

    private CommonModel getCommonModel(DemoVo demoVo) {
        CommonModel commonModel = new CommonModel();
        commonModel.setDatas(demoVo);
        return commonModel;
    }



}
