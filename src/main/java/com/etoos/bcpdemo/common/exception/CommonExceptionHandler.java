package com.etoos.bcpdemo.common.exception;

import com.etoos.bcpdemo.common.model.CommonModel;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.assign.primitive.PrimitiveUnboxingDelegate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.net.ssl.HttpsURLConnection;
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
        CommonModel commonModel = commonException.getCommonModel();
        return handleExceptionInternal(commonException, commonModel, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException runtimeException, WebRequest webRequest) {
        log.error("{} \r\n {}", runtimeException, webRequest);
        Object body = null;

        if (runtimeException instanceof CommonException) {
            runtimeException = (CommonException) runtimeException;
            body = ((CommonException) runtimeException).getCommonModel();
        } else {
            body = runtimeException.getMessage();
        }

        return this.handleExceptionInternal(runtimeException, body, null, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }


    @Override
    protected ResponseEntity<Object> handleBindException(BindException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(exception, exception.getMessage(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (Objects.isNull(body))
            body = ex.getMessage();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }


}
