package com.etoos.bcpdemo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CommonException extends ResponseStatusException {

    public CommonException(HttpStatus status) {
        super(status);
    }

    public CommonException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public CommonException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }


}
