package com.etoos.common.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.validation.FieldError;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErrorVo {

    private String objectName;

    private String field;

    private String code;

    private Object rejectedValue;

    private String message;

    public static FieldErrorVo create(FieldError fieldError) {

        return new FieldErrorVo(
                fieldError.getObjectName()
                , fieldError.getField()
                , fieldError.getCode()
                , fieldError.getRejectedValue()
                , fieldError.getDefaultMessage()
        );
    }

}
