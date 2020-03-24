package com.etoos;

import com.etoos.bcp.sample.controller.SampleController;
import com.etoos.bcp.sample.model.SampleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

@Component
public class AppRunner implements ApplicationRunner {



    @Autowired
    SampleController sampleController;


    @Autowired
    DispatcherServlet dispatcherServlet;


    @Override
    public void run(ApplicationArguments args) throws Exception {


        System.out.println(dispatcherServlet);

//        sampleController.findSample(new SampleVo());
    }
}
