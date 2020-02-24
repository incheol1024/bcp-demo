package com.etoos.bcpdemo.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Info implements Serializable {

    private String code = "200";

    private String message = "success";


}
