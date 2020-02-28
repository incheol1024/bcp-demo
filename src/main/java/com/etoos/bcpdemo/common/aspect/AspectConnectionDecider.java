package com.etoos.bcpdemo.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class AspectConnectionDecider {

    @Pointcut("@annotation(com.etoos.bcpdemo.common.aspect.DatabaseRouter)")
    public void pointCutPostgresDecision() {
    }

    @Before("pointCutPostgresDecision()")
    public void proceedBefore(JoinPoint joinPoint) throws Throwable {
        log.info("start to decide connection");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DatabaseRouter annotation = method.getAnnotation(DatabaseRouter.class);

        if (Objects.nonNull(annotation)) {
            DataSourceKeyThreadHolder.setDataSourceKey(annotation.value());
        }
        log.info("end to decide connection");
    }

    @After("pointCutPostgresDecision()")
    public void proceedAfter(JoinPoint joinPoint) {
        DataSourceKeyThreadHolder.clearDataSourceKey();
    }
}
