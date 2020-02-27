package com.etoos.bcpdemo.common.exception;

import com.etoos.bcpdemo.common.model.CommonModel;
import com.etoos.bcpdemo.common.model.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NotFoundException.class) // ResponseStatusException 사용한다면 NotFoundException 필요할까요?
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest webRequest) {
        return this.handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(CommonException.class) // ResponseStatusException 사용한다면 CommonException 필요할까요?
    public ResponseEntity<Object> handleCommonException(CommonException commonException, WebRequest webRequest) {
        return handleExceptionInternal(commonException, commonException.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException runtimeException, WebRequest webRequest) {
        if (runtimeException instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException)runtimeException;
            HttpStatus httpStatus = responseStatusException.getStatus();
            Object body = responseStatusException.getReason();
            return this.handleExceptionInternal(runtimeException, body, null, httpStatus, webRequest);
        }
        return this.handleExceptionInternal(runtimeException, runtimeException.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
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


        Info info = Info.create(String.valueOf(status.value()), status.name(), body);
        CommonModel commonModel = new CommonModel();
        commonModel.setInfo(info);

/*
        if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            ValidationResult validationResult = ValidationResult.create(bindingResult);
            info = Info.create(String.valueOf(status.value()), status.name(), validationResult);
        } else {
            info = Info.create(String.valueOf(status.value()), status.name(), ex.getMessage());
        }
*/

        return super.handleExceptionInternal(ex, commonModel, headers, status, request);
    }


}
