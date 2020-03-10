package com.etoos.common.util.lang;

import com.etoos.common.constants.SystemConstants;

/**
 * <pre>
 * com.etoos.common.util.lang
 *   |_ ExtRandomStringUtils.java
 * </pre>
 *
 * 1. 업무명         :
 * 2. 단위 업무명  :
 * @Class     : ExtRandomStringUtils
 * @Author    : JUNG YEON HO
 * @Since     : 2020. 3. 6. 오전 11:27:36
 * @Version   : 1.0
 * Copyright (c) ETOOS.
 * -------------------------------------------------------------------
 * Modification Information
 * -------------------------------------------------------------------
 * 수정일                                                     수정자                                         수정 내용
 * -------------------------------------------------------------------
 * 2020. 3. 6.                    JUNG YEON HO              [ETOOS] 최초 생성
 */
public class ExtRandomStringUtils extends org.apache.commons.lang3.RandomStringUtils {

    // 임시비밀번호용
    public static String tempPw() {
        return random(5, SystemConstants.RANDOM_STRINGS);
    }

    // 인증번호 발송용 (영문(대문자) + 숫자 8자리)
    public static String certNo() {
        return random(8, SystemConstants.RANDOM_UPPER_STRINGS + SystemConstants.RANDOM_INT_STRINGS);
    }

    // 인증번호 발송용 (숫자 4자리)
    public static String certNoInt() {
        return random(4, SystemConstants.RANDOM_INT_STRINGS);
    }

    public static String randomCode() {
        return random(7, SystemConstants.RANDOM_STRINGS);
    }

    public static String randomCode(int count) {
        return random(count, SystemConstants.RANDOM_STRINGS);
    }

}
