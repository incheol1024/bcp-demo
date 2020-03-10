package com.etoos.common.util.lang;

import org.apache.commons.lang3.ObjectUtils;


/**
 * <pre>
 * com.etoos.common.util.lang
 *   |_ ExtObjectUtils.java
 * </pre>
 *
 * 1. 업무명         :
 * 2. 단위 업무명  :
 * @Class     : ExtObjectUtils
 * @Author    : JUNG YEON HO
 * @Since     : 2020. 3. 6. 오전 10:53:31
 * @Version   : 1.0
 * Copyright (c) ETOOS.
 * -------------------------------------------------------------------
 * Modification Information
 * -------------------------------------------------------------------
 * 수정일                                                     수정자                                         수정 내용
 * -------------------------------------------------------------------
 * 2020. 3. 6.                    JUNG YEON HO              [ETOOS] 최초 생성
 */
public class ExtObjectUtils extends ObjectUtils {

    /**
     * <p>
     * object null 체크 <code>null</code>.
     * </p>
     *
     */
    public static boolean isNull(Object object) {
        return object != null ? false : true;
    }

    /**
     * <p>
     * object null 체크 <code>null</code>.
     * </p>
     *
     */
    public static String defaultString(Object object) {
        if (object != null) {
            return object.toString();
        } else {
            return "";
        }
    }

}
