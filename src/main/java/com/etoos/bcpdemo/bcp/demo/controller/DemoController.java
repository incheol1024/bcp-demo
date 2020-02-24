package com.etoos.bcpdemo.bcp.demo.controller;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.service.DemoService;
import com.etoos.bcpdemo.common.aspect.TimeChecker;
import com.etoos.bcpdemo.common.model.CommonModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
public class DemoController {

    private DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @PostMapping("")
    @TimeChecker
    public CommonModel createEntity(@Validated(value = Create.class) DemoVo demoVo) {
        log.info("{}", demoVo);
        return demoService.createEntity(demoVo);
    }

    @GetMapping("/{id}")
    @TimeChecker
    public DemoVo findEntity(@PathVariable long id) {
        return demoService.findEntity(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemoVo> updateEntity(@Validated(value = Update.class) DemoVo demoVo) {
        demoVo = demoService.updateEntity(demoVo);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(demoVo, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntity(@PathVariable long id) {
        HttpStatus httpStatus = demoService.deleteEntity(id);
        return ResponseEntity.status(httpStatus).build();
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
