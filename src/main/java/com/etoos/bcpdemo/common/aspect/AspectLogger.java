package com.etoos.bcpdemo.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

@Component
@Aspect
@Slf4j
public class AspectLogger {

    /**
     * 포인트것을 정의해 놓은 뒤 여러곳에서 사용할 수 있습니다.
     * 아래 예제는 애너테이션 기반의 포인트컷을 정의하였고
     * 해당 애너테이션을 선언된 그 위치가 포인트컷으로 사용됩니다.
    */
    @Pointcut("@annotation(com.etoos.bcpdemo.common.aspect.TimeChecker)")
    public void timeCheckerPointcut(){}

    @Around("timeCheckerPointcut()")
    public Object checkTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        Instant instant = Instant.now();
        // @Around 사용시 반드시 proceed 를 호출해야 합니다. 안하면 aspect를 적용한 해당 메소드가 호출이 안됨
        Object proceed = proceedingJoinPoint.proceed();
        log.info("{} spend time {}[ms]", method, Duration.between(instant, Instant.now()).toMillis());
        return proceed;
    }

    @Before("execution(* com.etoos.bcpdemo.bcp.demo.controller.DemoController.*(..))")
    public void checkBefore(JoinPoint joinPoint) {
        System.out.println("==================before===================");
    }


    @After("execution(* com.etoos.bcpdemo.bcp.demo.controller.DemoController.*(..))")
    public void checkAfter() {
        System.out.println("==================after===================");
    }







}
