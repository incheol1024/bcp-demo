package com.etoos.bcpdemo.common.model;

import com.etoos.bcpdemo.common.constant.InfoMessages;
import lombok.Data;

import java.io.Serializable;

@Data
public class Info implements Serializable {

    private String code = InfoMessages.getDefaultCode();

    private String message = InfoMessages.getDefaultMessage();

    private Object detail;


    public Info() {

    }

    private Info(String code, String message, Object detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public static Info create(String code, String message, Object detail) {
        return new Info(code, message, detail);
    }


}
