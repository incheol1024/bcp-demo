package com.etoos.bcpdemo.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest webRequest) {
        log.error("{} \r\n {}", exception, webRequest);

        return this.handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }


    @ExceptionHandler(CommonException.class)
    public ResponseEntity handleCommonException(CommonException commonException, WebRequest webRequest) {
        log.error("{} \r\n {}", commonException.getStackTrace(), webRequest);
        DemoErrorMessage errorMessage = commonException.getErrorMessage();
        return handleExceptionInternal(commonException, errorMessage.getMessage(), new HttpHeaders(), errorMessage.getStatus(), webRequest);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException runtimeException, WebRequest webRequest) {
        log.error("{} \r\n {}", runtimeException, webRequest);
        return this.handleExceptionInternal(runtimeException, runtimeException.getMessage(), null, null, webRequest);
    }



    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return this.handleExceptionInternal(ex, ex.getAllErrors(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (Objects.isNull(body))
            body = ex.getMessage();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }




}
