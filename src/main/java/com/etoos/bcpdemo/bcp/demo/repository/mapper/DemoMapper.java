package com.etoos.bcpdemo.bcp.demo.repository.mapper;

import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DemoMapper {
    List<DemoVo> findById(long id);
    void insertDemo(DemoVo demoVo);
    void updateDemo(DemoVo demoVo);
    void deleteDemo(DemoVo demoVo);

    List<DemoVo> selectAllDemo();
}
