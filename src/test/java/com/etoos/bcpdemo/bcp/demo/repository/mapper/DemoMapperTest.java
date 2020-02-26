package com.etoos.bcpdemo.bcp.demo.repository.mapper;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DemoMapperTest {

    @Autowired
    private DemoMapper demoMapper;

    @Autowired
    private DemoRepository demoRepository;

    @Test
    @Rollback(value = false)
    void insertDemoHibernate() {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setName("Aa");
        demoRepository.save(demoEntity);
    }

    @Test
    void insertDemo() {
        DemoVo demoVo = new DemoVo();
        demoVo.setId(1);
        demoVo.setName("aa");
        demoMapper.insertDemo(demoVo);
    }


}