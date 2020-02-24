package com.etoos.bcpdemo.common.exception;

import com.etoos.bcpdemo.common.model.CommonModel;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    CommonModel commonModel;

    public CommonException(CommonModel commonModel) {
        this.commonModel = commonModel;
    }
}
