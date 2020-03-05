package com.etoos.bcpdemo.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CommonException {


    public NotFoundException(HttpStatus status) {
        super(status);
    }

    public NotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public NotFoundException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
