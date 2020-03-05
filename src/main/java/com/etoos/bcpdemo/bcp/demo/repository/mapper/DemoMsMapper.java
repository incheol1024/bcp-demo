package com.etoos.bcpdemo.bcp.demo.repository.mapper;

import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DemoMsMapper {

    void insertDemo(DemoVo demoVo);
}
