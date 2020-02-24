package com.etoos.bcpdemo.bcp.demo.controller;

import com.etoos.bcpdemo.bcp.demo.model.entity.RedisEntity;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {


    @Autowired
    RedisRepository redisRepository;

    @GetMapping("/{id}")
    public RedisEntity get(@PathVariable long id) {
        log.info("{}", id);
        return redisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    @PostMapping("")
    public RedisEntity post(RedisEntity redisEntity) {
        log.info("{}", redisEntity);
        return redisRepository.save(redisEntity);
    }


}
