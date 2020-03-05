package com.etoos.bcpdemo.bcp.msdemo.service;

import com.etoos.bcpdemo.bcp.msdemo.entity.MsDemoEntity;
import com.etoos.bcpdemo.bcp.msdemo.repository.MsDemoRepository;
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
@DatabaseRouter(DataSourceDirection.POSTGRES_MASTER)
public class MsDemoService {

    @Autowired
    MsDemoRepository msDemoRepository;

    @DatabaseRouter(DataSourceDirection.MS_SQL_MASTER)
    @Transactional(transactionManager = "transactionManagerMsSqlJpa") // transactionManagerPostgresJpa transactionManagerMsSqlJpa
    public CommonModel createMsDemo(MsDemoEntity msDemoEntity) {
        MsDemoEntity save = msDemoRepository.save(msDemoEntity);
        if(Objects.nonNull(save))
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonModel.create(save);
    }



}
