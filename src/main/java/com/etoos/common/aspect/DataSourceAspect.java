package com.etoos.common.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.etoos.common.constants.SystemConstants;
import com.etoos.common.database.ContextHolder;
import com.etoos.common.database.DataSource;
import com.etoos.common.database.DataSourceType;

@Aspect
@Component
@Order(value=1)
public class DataSourceAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired(required=true) private HttpServletRequest request;

    @Pointcut("execution(* com.etoos..*Service.*(..))")
    public void commonPointcut(){}

    @Around("commonPointcut()")
    public Object aroundServiceProfiling(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug(SystemConstants.LINE);
        Object returnValue;
        try {
            // Annotation을 읽어 들이기 위해 현재 Method를 읽어 들임
            final String methodName = joinPoint.getSignature().getName();
            final MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            if(method.getDeclaringClass().isInterface()){
                method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
            }
            // Annotation을 가져온다
            DataSource dataSource = method.getAnnotation(DataSource.class);
            if(dataSource != null){
                // Method에 해당 DataSource관련 설정이 있는경우는 DataSource의 value를 읽어들임
                ContextHolder.setDataSourceType(dataSource.value());
            }else{
                // Method에 해당 DataSource관련 설정이 없는경우는 DataSource의 DEFAULE를 읽어들임
                ContextHolder.setDataSourceType(DataSourceType.DEFAULT);
            }
            log.debug("DataSource : " + ContextHolder.getDataSourceType());
            returnValue = joinPoint.proceed();
        } finally {
            ContextHolder.clearDataSourceType();
        }
        log.debug(SystemConstants.LINE);
        return returnValue;
    }

}
