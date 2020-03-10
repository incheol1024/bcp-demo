package com.etoos.common.util.lang;


/**
 * <pre>
 * com.etoos.common.util.lang
 *   |_ ExtSystemUtils.java
 * </pre>
 *
 * 1. 업무명         :
 * 2. 단위 업무명  :
 * @Class     : ExtSystemUtils
 * @Author    : JUNG YEON HO
 * @Since     : 2020. 3. 6. 오전 11:28:12
 * @Version   : 1.0
 * Copyright (c) ETOOS.
 * -------------------------------------------------------------------
 * Modification Information
 * -------------------------------------------------------------------
 * 수정일                                                     수정자                                         수정 내용
 * -------------------------------------------------------------------
 * 2020. 3. 6.                    JUNG YEON HO              [ETOOS] 최초 생성
 */
public class ExtSystemUtils extends org.apache.commons.lang3.SystemUtils {

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
