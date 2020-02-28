package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.mapper.DemoMapper;
import com.etoos.bcpdemo.common.model.CommonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoBatisService {

    @Autowired
    DemoMapper demoMapper;


    public CommonModel createDemo(DemoVo demoVo) {
        demoMapper.insertDemo(demoVo);
        CommonModel commonModel = new CommonModel();
        commonModel.setDatas(demoVo);
        return commonModel;
    }


}
