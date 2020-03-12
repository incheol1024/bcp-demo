package com.etoos.bcp.sample.controller;

import com.etoos.bcp.sample.model.SampleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
@Slf4j
public class CacheController {

    @Cacheable(value = "post-single", key = "#sampleVo", cacheManager = "cacheManager")
    @GetMapping("/find")
    public SampleVo findSample(SampleVo sampleVo) {
        log.info("{}", sampleVo);
        return sampleVo;
    }

}
