package com.etoos.bcp.sample.service;

import java.util.List;

import com.etoos.bcp.sample.model.SampleVo;
import com.etoos.common.exception.CommonException;

public interface SampleService {

    public List<SampleVo> retrieveMybatis() throws CommonException;

    public List<SampleVo> retrieveMybatisRead() throws CommonException;
}
