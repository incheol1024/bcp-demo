package com.etoos.bcp.sample.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.etoos.bcp.sample.model.SampleVo;

@Mapper
public interface SampleMapper {
    List<SampleVo> selectUser();


    void insertUser(SampleVo sampleVo);


    SampleVo findSample(SampleVo sampleVo);


    void updateSample();


    void deleteSample();


}
