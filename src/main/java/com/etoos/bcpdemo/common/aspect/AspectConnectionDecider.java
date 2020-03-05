package com.etoos.bcpdemo.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@Order(0)
@Slf4j
public class AspectConnectionDecider {


    @Pointcut("@annotation(com.etoos.bcpdemo.common.aspect.DatabaseRouter)")
    public void pointCutPostgresDecision() {
    }

    @Before(value = "(@within(DatabaseRouter) || pointCutPostgresDecision()) && execution(* com.etoos.bcpdemo.bcp.*.service.*.*(..))")
    public void proceedBefore(JoinPoint joinPoint) throws Throwable {
        DatabaseRouter annotation = joinPoint.getTarget().getClass().getAnnotation(DatabaseRouter.class);
        if(Objects.nonNull(annotation)) {
            DataSourceKeyThreadHolder.setDataSourceKey(annotation.value());
        }

//        log.info("start to decide connection");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        annotation = method.getAnnotation(DatabaseRouter.class);

        if (Objects.nonNull(annotation)) {
            DataSourceKeyThreadHolder.setDataSourceKey(annotation.value());
        }
        log.info("end to decide connection " + DataSourceKeyThreadHolder.getDataSourceKey() );
    }

    @After(value = "@within(DatabaseRouter) ||pointCutPostgresDecision() && execution(* com.etoos.bcpdemo.bcp..*.service.*(..))")
    public void proceedAfter() {
        DataSourceKeyThreadHolder.clearDataSourceKey();
    }
}
