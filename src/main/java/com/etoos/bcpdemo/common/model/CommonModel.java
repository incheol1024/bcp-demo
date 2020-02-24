package com.etoos.bcpdemo.common.model;

import lombok.Data;

import java.util.List;

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
public class CommonModel {

    private ApiInfo info;

    private Object datas;


}
