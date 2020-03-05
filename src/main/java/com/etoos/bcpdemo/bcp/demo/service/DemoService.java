package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.common.model.CommonModel;

public interface DemoService {

    CommonModel findDemo(long id);

    CommonModel createDemo(DemoVo demoVo);

    CommonModel updateDemo(DemoVo demoVo);

    CommonModel deleteDemo(long id);

}
