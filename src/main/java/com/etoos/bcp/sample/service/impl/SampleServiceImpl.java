package com.etoos.bcp.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoos.bcp.sample.model.SampleVo;
import com.etoos.bcp.sample.repository.SampleMapper;
import com.etoos.bcp.sample.service.SampleService;
import com.etoos.common.database.DataSource;
import com.etoos.common.database.DataSourceType;
import com.etoos.common.exception.CommonException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleMapper sampleMapper;

    @Override
    public List<SampleVo> retrieveMybatis() throws CommonException {
        return sampleMapper.selectUser();
    }

    @Override
    public SampleVo insertSample(SampleVo sampleVo) {
        sampleMapper.insertUser(sampleVo);
        return sampleVo;
    }

    @Override
    @DataSource(DataSourceType.BCPREAD)
    public List<SampleVo> retrieveMybatisRead() throws CommonException {
        return sampleMapper.selectUser();
    }

    @Override
    public SampleVo findSample(SampleVo sampleVo) {
        return sampleMapper.findSample(sampleVo);
    }

    @Override
    public SampleVo updateSample(SampleVo sampleVo) {
        sampleMapper.updateSample();
        return sampleVo;
    }

    @Override
    public SampleVo deleteSample(SampleVo sampleVo) {
        sampleMapper.deleteSample();
        return sampleVo;
    }

}
