package com.etoos.bcpdemo.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.http.HttpProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;


@Slf4j
public class DemoInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 공통 헤더 설정
        addResponseHeaders(response);
        boolean committed = response.isCommitted();
        System.out.println("commited: " + committed);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("afterConcurrentHandlingStarted");
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    private void addResponseHeaders(HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader(HttpHeaders.DATE, LocalDateTime.now().toString());
        httpServletResponse.addHeader(HttpHeaders.ACCEPT_ENCODING, HttpProperties.Encoding.DEFAULT_CHARSET.name());
        httpServletResponse.setHeader("test-header", "test-value");
    }
}
