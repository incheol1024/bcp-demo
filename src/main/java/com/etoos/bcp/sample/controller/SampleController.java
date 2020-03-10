package com.etoos.bcp.sample.controller;

import com.etoos.bcp.common.model.CommonVo;
import com.etoos.bcp.sample.model.SampleVo;
import com.etoos.bcp.sample.service.SampleService;
import com.etoos.common.aspect.TimeCheckerAspect;
import com.etoos.common.constants.CrudInterface;
import com.etoos.common.constants.CrudInterface.Create;
import com.etoos.common.model.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "SampleController Api annotation", tags = {"샘플"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RestController
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    SampleService sampleService;

    @PostMapping(value = "")
    @ApiOperation(value = "DemoEntity를 생성합니다. id, name 을 메세지에 담아서 보내주세요.", tags = {"조회"}, response = CommonVo.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "값을 입력하지 말아주세요.")
            , @ApiImplicitParam(name = "name", value = "등록할 이름을 보내주세요. ex) hong gil dong")
    })
    @TimeCheckerAspect
    public List<SampleVo> createEntity(@Validated(value = Create.class) @RequestBody SampleVo demoVo) {
        log.info("{}", demoVo);
        return sampleService.retrieveMybatis();
    }

    @GetMapping(value = ""
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "DemoEntity를 조회합니다. id를 url 끝에 작성하여 보내주세요."
            , produces = MediaType.APPLICATION_JSON_VALUE, response = ResponseVo.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "조회할 아이디 값 ex) 1")})
    @TimeCheckerAspect
    public ResponseVo findSample(SampleVo sampleVo) {
        return ResponseVo.create(sampleService.findSample(sampleVo));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "DemoEntity를 수정합니다. id와 변경할 값을 보내주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , response = ResponseVo.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "변경하고 싶은 아이디 값 ex) 1")
            , @ApiImplicitParam(name = "name", value = "변경할 이름 ex) kim gil dong")})
    public ResponseVo updateDemo(@Validated(value = CrudInterface.Update.class) SampleVo sampleVo) {
        return ResponseVo.create(sampleService.updateSample(sampleVo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "DemoEntity를 삭제합니다. 삭제할 아이디를 url 패스 끝에 적어주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , response = ResponseVo.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id"
            , value = "삭제할 아이디 값 ex) 1"
            , required = true
            , dataType = "long"
            , paramType = "path")})
    public ResponseVo deleteDemo(@PathVariable SampleVo sampleVo) {
        return ResponseVo.create(sampleService.deleteSample(sampleVo));
    }

}
