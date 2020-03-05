package com.etoos.bcpdemo.bcp.demo.controller;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.service.DemoService;
import com.etoos.bcpdemo.bcp.demo.service.DemoServiceJpa;
import com.etoos.bcpdemo.common.aspect.TimeChecker;
import com.etoos.bcpdemo.common.model.CommonModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.etoos.bcpdemo.common.constant.CrudInterface.Create;
import static com.etoos.bcpdemo.common.constant.CrudInterface.Update;

@Slf4j
@RestController
@RequestMapping("/demo")
// @Api 애너테이션은 뭐하는건지 모르겠음
@Api(value = "DemoController Api annotation"
        , protocols = "HTTP"
        , consumes = MediaType.APPLICATION_JSON_VALUE
        , produces = MediaType.APPLICATION_JSON_VALUE)
public class DemoController {


    @Autowired
    @Qualifier("demoServiceJpa")
    DemoService demoServiceJpa;


    @PostMapping(value = ""
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "DemoEntity를 생성합니다. id, name 을 메세지에 담아서 보내주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , response = CommonModel.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "값을 입력하지 말아주세요.")
            , @ApiImplicitParam(name = "name", value = "등록할 이름을 보내주세요. ex) hong gil dong")})
    @CachePut(value = "DemoVo", key = "#demoVo.id") // 최종 리턴 결과만 캐시에 저장함. 캐시에서 찾을 생각을 안함.
    @TimeChecker
    public CommonModel createDemo(@Validated(value = Create.class) @RequestBody DemoVo demoVo) {
        log.info("{}", demoVo);
        return demoServiceJpa.createDemo(demoVo);
    }

    @GetMapping(value = "/{id}"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "DemoEntity를 조회합니다. id를 url 끝에 작성하여 보내주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE, response = CommonModel.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "조회할 아이디 값 ex) 1")})
    @Cacheable(value = "DemoVo", key = "#id") // 캐시에서 먼저 조회하고, 없으면 핸들러 메소드 실행후 결과값 캐시에 저장
    @TimeChecker
    public CommonModel findDemo(@PathVariable long id) {
        return demoServiceJpa.findDemo(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "DemoEntity를 수정합니다. id와 변경할 값을 보내주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , response = CommonModel.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "변경하고 싶은 아이디 값 ex) 1")
            , @ApiImplicitParam(name = "name", value = "변경할 이름 ex) kim gil dong")})
    @CachePut(value = "DemoVo", key = "#demoVo.id")
    public CommonModel updateDemo(@Validated(value = Update.class) DemoVo demoVo) {
        return demoServiceJpa.updateDemo(demoVo);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "DemoEntity를 삭제합니다. 삭제할 아이디를 url 패스 끝에 적어주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , response = CommonModel.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id"
            , value = "삭제할 아이디 값 ex) 1"
            , required = true
            , dataType = "long"
            , paramType = "path")})
    @CacheEvict(value = "DemoVo", key = "#id") // 캐시 삭제
    public CommonModel deleteDemo(@PathVariable long id) {
        return demoServiceJpa.deleteDemo(id);
    }

    @GetMapping("/test")
    public ResponseEntity<List<DemoEntity>> listResponseEntity() {
        DemoEntity entity = new DemoEntity();
        entity.setId(1);
        entity.setName("incheol");
        List<DemoEntity> demoEntities = new ArrayList<>();
        demoEntities.add(entity);
        demoEntities.add(entity);
        return new ResponseEntity<>(demoEntities, HttpStatus.OK);
    }

}
