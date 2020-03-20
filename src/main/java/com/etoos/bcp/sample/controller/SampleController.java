package com.etoos.bcp.sample.controller;

import com.etoos.bcp.common.model.CommonVo;
import com.etoos.bcp.sample.model.SampleVo;
import com.etoos.bcp.sample.service.SampleService;
import com.etoos.common.aspect.TimeCheckerAspect;
import com.etoos.common.constants.CrudInterface;
import com.etoos.common.constants.CrudInterface.Create;
import com.etoos.common.exception.CommonException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

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
    public List<SampleVo> createEntity(@Validated(value = Create.class) @RequestBody SampleVo sampleVo) {
        log.info("{}", sampleVo);
        return sampleService.retrieveMybatis();
    }

    @PostMapping("/create")
    public SampleVo createSample(@Validated(value = Create.class) @RequestBody SampleVo sampleVo) {
        log.info("{}", sampleVo);
        return sampleService.insertSample(sampleVo);
    }

    @GetMapping(value = ""
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "DemoEntity를 조회합니다. id를 url 끝에 작성하여 보내주세요."
            , produces = MediaType.APPLICATION_JSON_VALUE, response = SampleVo.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "조회할 아이디 값 ex) 1")})
    @TimeCheckerAspect
    public SampleVo findSample(@Valid SampleVo sampleVo) {
        sampleVo.setId(100);
        return sampleVo;
//        return sampleService.findSample(sampleVo);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "DemoEntity를 수정합니다. id와 변경할 값을 보내주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , response = SampleVo.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "변경하고 싶은 아이디 값 ex) 1")
            , @ApiImplicitParam(name = "name", value = "변경할 이름 ex) kim gil dong")})
    public SampleVo updateDemo(@Validated(value = CrudInterface.Update.class) SampleVo sampleVo) {
        return sampleService.updateSample(sampleVo);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "DemoEntity를 삭제합니다. 삭제할 아이디를 url 패스 끝에 적어주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , response = SampleVo.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id"
            , value = "삭제할 아이디 값 ex) 1"
            , required = true
            , dataType = "long"
            , paramType = "path")})
    public SampleVo deleteDemo(@PathVariable SampleVo sampleVo) {
        return sampleService.deleteSample(sampleVo);
    }


    @PostMapping("/file")
    public ResponseEntity createFile(@RequestPart(name = "files") MultipartFile multipartFile) {
        log.info("{}", multipartFile.getClass());
        try {
            multipartFile.transferTo(Paths.get("./" + multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new CommonException("io error", e);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/file/find")
    public ResponseEntity findFile() {

        ResponseEntity entity = null;
        try {
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get("./originFileName")));
            entity = new ResponseEntity(resource, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity;
    }


    @GetMapping("/nuxt")
    public ResponseEntity<String> getNuxt(@RequestParam("name") String name) {
        return ResponseEntity.ok("Hello " + name);
    }
}
