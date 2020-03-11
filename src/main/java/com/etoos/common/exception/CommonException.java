package com.etoos.common.exception;

public class CommonException extends RuntimeException {

    private static final long serialVersionUID = -6226886069385362812L;

    public CommonException() {
        super();
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }
}
