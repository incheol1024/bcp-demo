package com.etoos.common.model;

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

}
