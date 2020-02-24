package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.common.aspect.TimeChecker;
import com.etoos.bcpdemo.common.exception.NotFoundException;
import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import com.etoos.bcpdemo.common.model.CommonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@TimeChecker
public class DemoService {

    private DemoRepository demoRepository;


    @Autowired
    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
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

    public DemoVo findEntity(long id) {
        DemoEntity demoEntity = demoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Entity id:" + id));
        DemoVo demoVo = new DemoVo();
        return demoVo.valueOf(demoEntity);
    }

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


    public HttpStatus deleteEntity(long id) {
        demoRepository.deleteById(id);
        return HttpStatus.OK;
    }

    public DemoEntity demoEntity() {
        Optional<DemoEntity> byId = demoRepository.findById(1L);
        return byId.get();
    }

}
