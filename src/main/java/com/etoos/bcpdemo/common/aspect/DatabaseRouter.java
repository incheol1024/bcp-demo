package com.etoos.bcpdemo.common.aspect;

import com.etoos.bcpdemo.common.constant.DataSourceDirection;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DatabaseRouter {

//    DataSourceDirection value() default DataSourceDirection.POSTGRES_MASTER;
    DataSourceDirection value();

}
