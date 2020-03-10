package com.etoos.common.util.lang;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.math.NumberUtils;


/**
 * <pre>
 * com.etoos.common.util.lang
 *   |_ ExtNumberUtils.java
 * </pre>
 *
 * 1. 업무명         :
 * 2. 단위 업무명  :
 * @Class     : ExtNumberUtils
 * @Author    : JUNG YEON HO
 * @Since     : 2020. 3. 6. 오전 10:52:48
 * @Version   : 1.0
 * Copyright (c) ETOOS.
 * -------------------------------------------------------------------
 * Modification Information
 * -------------------------------------------------------------------
 * 수정일                                                     수정자                                         수정 내용
 * -------------------------------------------------------------------
 * 2020. 3. 6.                    JUNG YEON HO              [ETOOS] 최초 생성
 */
public class ExtNumberUtils extends NumberUtils {

    /**
     *
     * <pre>
     * 1. 처리내용 : Parse float
     * </pre>
     *
     * @Author : [ So WooSung ]
     * @Date : 2017. 9. 19. 오후 7:21:53
     * @Method Name : ParseFloat
     * @param arg
     * @return
     *
     */
    public static float parseFloat(Object arg) {

        float result = Float.MIN_VALUE;

        try {
            result = Float.parseFloat(String.valueOf(arg));
        } catch (NumberFormatException nfex) {
            result = Float.MIN_VALUE;
        }

        return result;

    }

    /**
     *
     * <pre>
     * 1. 처리내용 : Parse integer
     * </pre>
     *
     * @Author : [ So WooSung ]
     * @Date : 2017. 9. 19. 오후 7:22:04
     * @Method Name : ParseInteger
     * @param arg
     * @return
     *
     */
    public static int parseInteger(Object arg) {

        int result = NumberUtils.INTEGER_ZERO;

        try {
            result = new BigDecimal(String.valueOf(arg)).setScale(0, RoundingMode.HALF_EVEN).intValueExact();
        } catch (NumberFormatException nfex) {
            result = NumberUtils.INTEGER_ZERO;
        }

        return result;
    }

    /**
     *
     * <pre>
     * 1. 처리내용 : Parse double
     * </pre>
     *
     * @Author : [ So WooSung ]
     * @Date : 2017. 9. 19. 오후 7:22:16
     * @Method Name : ParseDouble
     * @param arg
     * @return
     *
     */
    public static double parseDouble(Object arg) {

        double result = NumberUtils.DOUBLE_ZERO;

        try {
            result = NumberUtils.toDouble(String.valueOf(arg));
        } catch (NumberFormatException nfex) {
            result = NumberUtils.DOUBLE_ZERO;
        }

        return result;

    }

    /**
     *
     * <pre>
     * 1. 처리내용 : Parse long
     * </pre>
     *
     * @Author : [ So WooSung ]
     * @Date : 2017. 9. 19. 오후 7:22:24
     * @Method Name : ParseLong
     * @param arg
     * @return
     *
     */
    public static long parseLong(Object arg) {

        long result = NumberUtils.LONG_ZERO;

        try {
            result = NumberUtils.toLong(String.valueOf(arg));
        } catch (Exception ex) {
            result = NumberUtils.LONG_ZERO;
        }

        return result;
    }

}
