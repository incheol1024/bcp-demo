package com.etoos.common.model;

import com.etoos.bcp.common.model.CommonVo;
import com.etoos.bcp.sample.model.SampleVo;
import lombok.Data;

/**
 api_info: {
    err_code: 500
    err_message: server error
 },
 datas: {

 }
s
 */
@Data
public class ResponseVo {

    private ResponseInfoVo info;

    private Object datas;

    public static ResponseVo create(Object object) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setDatas(object);
        return responseVo;
    }
}
