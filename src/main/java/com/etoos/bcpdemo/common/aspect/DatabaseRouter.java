package com.etoos.bcpdemo.common.aspect;

import com.etoos.bcpdemo.common.constant.DataSourceDirection;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.lang.annotation.*;


/**
 * 해당 에너테이션을 사용하므로써 DataSource를 선택할 수 있습니다.
 * value는 필수 지정값이며 {@link DataSourceDirection} 을 사용하여
 * 미리 지정된 DataSource 만 사용 할 수 있습니다.
 *
*/
@Retention(value = RetentionPolicy.CLASS)
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DatabaseRouter {

    DataSourceDirection value();

}
