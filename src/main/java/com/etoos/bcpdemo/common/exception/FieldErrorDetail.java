package com.etoos.bcpdemo.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.validation.FieldError;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErrorDetail {

    private String objectName;

    private String field;

    private String code;

    private Object rejectedValue;

    private String message;

    public static FieldErrorDetail create(FieldError fieldError) {

        return new FieldErrorDetail(
                fieldError.getObjectName()
                , fieldError.getField()
                , fieldError.getCode()
                , fieldError.getRejectedValue()
                , fieldError.getDefaultMessage()
        );
    }

}
