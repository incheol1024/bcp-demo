package com.etoos.bcpdemo.common.model;

import lombok.Data;

import java.io.Serializable;

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
public class CommonModel implements Serializable {

    private Info info = new Info();

    private Object datas;


    public static CommonModel create(Object datas) {
        CommonModel commonModel = new CommonModel();
        commonModel.setDatas(datas);
        return commonModel;
    }

}
