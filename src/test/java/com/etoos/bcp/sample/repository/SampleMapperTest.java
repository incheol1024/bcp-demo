package com.etoos.bcp.sample.repository;

import com.etoos.bcp.sample.model.SampleVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class SampleMapperTest {

    @Autowired
    SampleMapper sampleMapper;

    @Test
    void selectUser() {
        List<SampleVo> sampleVos = sampleMapper.selectUser();
/*
        sampleVos.stream()
                .map(SampleVo::toString)
                .forEach(sampleVo -> log.info(sampleVo));
*/
    }

    @Test
    void insertUser() {
        SampleVo sampleVo = new SampleVo();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            sampleVo.setRegUser(UUID.randomUUID().toString());
            sampleMapper.insertUser(sampleVo);
        }
    }

    @Test
    void findSample() {
        SampleVo sample = new SampleVo();
        sample.setId(1);
        sampleMapper.findSample(sample);

    }

    @Test
    void updateSample() {
    }

    @Test
    void deleteSample() {
    }
}