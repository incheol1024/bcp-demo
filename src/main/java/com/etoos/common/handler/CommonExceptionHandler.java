package com.etoos.common.handler;

import com.etoos.common.exception.CommonException;
import com.etoos.common.exception.ValidationResult;
import com.etoos.common.model.ResponseErrorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Object> handleCommonException(CommonException commonException, WebRequest webRequest) {
        return handleExceptionInternal(commonException, commonException.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        return handleExceptionInternal(ex, ValidationResult.create(bindingResult), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        return handleExceptionInternal(ex, ValidationResult.create(bindingResult), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        log.error("{} \r\n {}", ex.getStackTrace(), request);
        if (Objects.isNull(body))
            body = ex.getMessage();

        ResponseErrorVo responseErrorVo = new ResponseErrorVo(status.toString(), LocalDateTime.now(), body);
        return super.handleExceptionInternal(ex, responseErrorVo, headers, status, request);
    }

}
