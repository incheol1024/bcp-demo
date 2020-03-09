package com.etoos.bcpdemo.common.aspect;

import lombok.extern.slf4j.Slf4j;
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
     * 아래 timeCheckerPointcut 예제는 애너테이션 기반의 포인트컷을 정의하였고
     * 해당 애너테이션이 선언된 그 위치가 포인트컷으로 사용됩니다.
     */
    @Pointcut("@annotation(com.etoos.bcpdemo.common.aspect.TimeChecker)")
    public void timeCheckerPointcut() {
    }

    /**
     * timeCheckerPointcut 포인트 컷(TimeChecker 애너테이션이 선언된)으로 지정된 곳에
     * Around 어드바이스를 적용하는 예제입니다.
     * @Around
     */
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

    /**
     * execution 표현식으로 포인트컷을 정의하여 사용하며
     * 예제의 표현식은 DemoController 클래스의 아규먼트가 0개 이상인 모든 메소드를 가리킵니다.
     */
    @Before("execution(* com.etoos.bcpdemo.bcp.demo.controller.DemoController.*(..))")
    public void checkBefore(JoinPoint joinPoint) {
        System.out.println("==================before===================");
    }


    @After("execution(* com.etoos.bcpdemo.bcp.demo.controller.DemoController.*(..))")
    public void checkAfter() {
        System.out.println("==================after===================");
    }


}
