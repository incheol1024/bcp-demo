package com.etoos.bcpdemo.common.model;

import com.etoos.bcpdemo.common.constant.InfoMessages;
import lombok.Data;

import java.io.Serializable;

@Data
public class Info implements Serializable {

    private String code = InfoMessages.getDefaultCode();

    private String message = InfoMessages.getDefaultMessage();


    public Info() {

    }



}
