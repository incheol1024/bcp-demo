package com.etoos.common.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class InfoMessages {

    private static String defaultCode;

    private static String defaultMessage;


    @Value("${response.info.default-code}")
    private void setDefaultCode(String defaultCode) {
        this.defaultCode = defaultCode;
    }

    @Value("${response.info.default-message}")
    private void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public static String getDefaultCode() {
        return defaultCode;
    }

    public static String getDefaultMessage() {
        return defaultMessage;
    }


}
