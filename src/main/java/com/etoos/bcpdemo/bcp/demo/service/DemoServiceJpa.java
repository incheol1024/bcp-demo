package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import com.etoos.bcpdemo.bcp.demo.repository.mapper.DemoMapper;
import com.etoos.bcpdemo.common.aspect.DatabaseRouter;
import com.etoos.bcpdemo.common.constant.DataSourceDirection;
import com.etoos.bcpdemo.common.exception.CommonException;
import com.etoos.bcpdemo.common.exception.NotFoundException;
import com.etoos.bcpdemo.common.model.CommonModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.UserTransaction;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@DatabaseRouter(DataSourceDirection.POSTGRES_MASTER)
public class DemoServiceJpa implements DemoService {

    @Autowired
    private DemoRepository demoRepository;

    @Override
    @DatabaseRouter(DataSourceDirection.POSTGRES_SLAVE_ONE)
    public CommonModel findDemo(long id) {
        DemoEntity demoEntity = demoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "Not Found Entity id:" + id));
        return CommonModel.create(DemoVo.createDemoVo(demoEntity));
    }

    @Override
    public CommonModel createDemo(DemoVo demoVo) {
        DemoEntity demoEntity = DemoEntity.createDemo(demoVo);
        demoEntity = demoRepository.save(demoEntity);
        return CommonModel.create(DemoVo.createDemoVo(demoEntity));
    }

    @Override
    public CommonModel updateDemo(DemoVo demoVo) {
        DemoEntity demoEntity = DemoEntity.createDemo(demoVo);
        demoEntity = demoRepository.save(demoEntity);
        return CommonModel.create(DemoVo.createDemoVo(demoEntity));
    }

    @Override
    public CommonModel deleteDemo(long id) {
        demoRepository.deleteById(id);
        return new CommonModel();
    }

}
