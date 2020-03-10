package com.etoos.bcp.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoos.bcp.common.model.CommonVo;
import com.etoos.bcp.sample.model.SampleVo;
import com.etoos.bcp.sample.service.SampleService;
import com.etoos.common.aspect.TimeCheckerAspect;
import com.etoos.common.constants.CrudInterface.Create;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value="SampleController Api annotation", tags={"샘플"}, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RestController
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    SampleService sampleService;

    @PostMapping(value = "")
    @ApiOperation(value = "DemoEntity를 생성합니다. id, name 을 메세지에 담아서 보내주세요.", tags={"조회"}, response = CommonVo.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "값을 입력하지 말아주세요.")
            , @ApiImplicitParam(name = "name", value = "등록할 이름을 보내주세요. ex) hong gil dong")
    })
    @TimeCheckerAspect
    public List<SampleVo> createEntity(@Validated(value = Create.class) @RequestBody SampleVo demoVo) {
        log.info("{}", demoVo);
        return sampleService.retrieveMybatis();
    }

}
