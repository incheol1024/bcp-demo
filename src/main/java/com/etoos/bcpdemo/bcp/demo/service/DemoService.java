package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import com.etoos.bcpdemo.bcp.demo.repository.mapper.DemoMapper;
import com.etoos.bcpdemo.common.aspect.DatabaseRouter;
import com.etoos.bcpdemo.common.constant.DataSourceDirection;
import com.etoos.bcpdemo.common.model.CommonModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
@Transactional
public class DemoService {

    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private DemoMapper demoMapper;


    @DatabaseRouter(DataSourceDirection.POSTGRES_MASTER)
    public CommonModel createDemo(DemoVo demoVo) {
        demoMapper.insertDemo(demoVo);
        CommonModel commonModel = new CommonModel();
        commonModel.setDatas(demoVo);
        return commonModel;
    }


    public CommonModel findNameById(long id) {
        DemoEntity demoEntity = demoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found id"));

        DemoVo demoVo = new DemoVo();
        demoVo.setId(demoEntity.getId());
        demoVo.setName(demoEntity.getName());

        CommonModel commonModel = new CommonModel();
        commonModel.setDatas(demoVo);

        return commonModel;
    }

    @DatabaseRouter(DataSourceDirection.POSTGRES_MASTER)
    public CommonModel findEntity(long id) {
        DemoEntity demoEntity = demoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found Entity id:" + id));
        DemoVo demoVo = new DemoVo();
        CommonModel commonModel = new CommonModel();
        commonModel.setDatas(commonModel);
        return commonModel;
    }

    @DatabaseRouter(DataSourceDirection.POSTGRES_SLAVE_ONE)
    public CommonModel createEntity(DemoVo demoVo) {
        DemoEntity demoEntity = new DemoEntity();
//        demoEntity.setValueOfDemoVo(demoVo);
        demoEntity.setName(demoVo.getName());
        demoEntity = demoRepository.save(demoEntity);
        demoVo.valueOf(demoEntity);

        CommonModel commonModel = new CommonModel();
        commonModel.setDatas(demoVo);
        return commonModel;
    }

    public CommonModel updateEntity(DemoVo demoVo) {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setName(demoVo.getName());
        demoEntity.setValueOfDemoVo(demoVo);
        demoEntity = demoRepository.save(demoEntity);
        demoVo.valueOf(demoEntity);
        CommonModel commonModel = new CommonModel();
        commonModel.setDatas(demoVo);
        return commonModel;
    }


    public CommonModel deleteEntity(long id) {
        demoRepository.deleteById(id);
        return new CommonModel();
    }

    public DemoEntity demoEntity() {
        Optional<DemoEntity> byId = demoRepository.findById(1L);
        return byId.get();
    }

}
