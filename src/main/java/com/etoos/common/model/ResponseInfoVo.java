package com.etoos.common.model;

import lombok.Data;

import java.io.Serializable;

import com.etoos.common.constants.InfoMessages;

@Data
public class ResponseInfoVo implements Serializable {

    private String code = InfoMessages.getDefaultCode();

    private String message = InfoMessages.getDefaultMessage();

    private Object detail;


    public ResponseInfoVo() {

    }

    private ResponseInfoVo(String code, String message, Object detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public static ResponseInfoVo create(String code, String message, Object detail) {
        return new ResponseInfoVo(code, message, detail);
    }


}
