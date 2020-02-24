/*
package com.etoos.bcpdemo.common.aspect;

import com.etoos.bcpdemo.common.exception.CommonException;
import com.etoos.bcpdemo.common.model.CommonModel;
import com.etoos.bcpdemo.common.model.Info;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectModelProviderAtException {


    @AfterThrowing(value = "execution(* com.etoos.bcpdemo.bcp..*.*(..))", throwing = "exception")
    public void setModelAfterThrowing(JoinPoint joinPoint, Exception exception) {
        CommonModel commonModel = new CommonModel();
        Info info = new Info();
        info.setCode("500");
        info.setMessage(exception.getMessage());
        commonModel.setInfo(info);
        exception = new CommonException(commonModel);
    }

}
*/
