package com.etoos.common.handler;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.etoos.common.exception.CommonException;
import com.etoos.common.exception.ValidationResult;
import com.etoos.common.model.ResponseInfoVo;
import com.etoos.common.model.ResponseVo;

import lombok.extern.slf4j.Slf4j;

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
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{} \r\n {}", ex.getStackTrace(), request);
        if (Objects.isNull(body))
            body = ex.getMessage();

        ResponseInfoVo responseInfoVo = ResponseInfoVo.create(String.valueOf(status.value()), status.name(), body);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setInfo(responseInfoVo);

        return super.handleExceptionInternal(ex, responseVo, headers, status, request);
    }

}
