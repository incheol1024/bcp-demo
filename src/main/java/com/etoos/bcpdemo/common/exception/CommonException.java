package com.etoos.bcpdemo.common.exception;

public abstract class CommonException extends RuntimeException {

    private final DemoErrorMessage errorMessage;

    public CommonException(DemoErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public CommonException(String message, DemoErrorMessage errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public CommonException(String message, Throwable cause, DemoErrorMessage errorMessage) {
        super(message, cause);
        this.errorMessage = errorMessage;
    }

    public CommonException(Throwable cause, DemoErrorMessage errorMessage) {
        super(cause);
        this.errorMessage = errorMessage;
    }

/*    public void setErrorMessage(DemoErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
*/

    public DemoErrorMessage getErrorMessage() {
        return errorMessage;
    }


}
