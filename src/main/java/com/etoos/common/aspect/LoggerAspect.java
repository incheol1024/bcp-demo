package com.etoos.common.aspect;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.etoos.common.constants.SystemConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LoggerAspect {

    @Pointcut("@annotation(com.etoos.common.aspect.TimeCheckerAspect)")
    public void timeCheckerPointcut() {}

    @Around("timeCheckerPointcut()")
    public Object checkTimeForEachPointCut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return getObjectAndPrintTime(proceedingJoinPoint);
    }

    private Object getObjectAndPrintTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        Instant instant = Instant.now();
        // @Around 사용시 반드시 proceed 를 호출해야 합니다. 안하면 aspect를 적용한 해당 메소드가 호출이 안됨
        Object proceed = proceedingJoinPoint.proceed();
        log.info("{} spend time {}[ms]", method, Duration.between(instant, Instant.now()).toMillis());
        return proceed;
    }

    @Before("timeCheckerPointcut()")
    public void checkBefore(JoinPoint joinPoint) {
        log.info(SystemConstants.LINE);
    }


    @After("timeCheckerPointcut()")
    public void checkAfter() {
        log.info(SystemConstants.LINE);
    }


}
