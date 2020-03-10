package com.etoos.common.util.lang;

import java.io.File;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;


/**
 * <pre>
 * com.etoos.common.util.lang
 *   |_ ExtStringUtils.java
 * </pre>
 *
 * 1. 업무명         :
 * 2. 단위 업무명  :
 * @Class     : ExtStringUtils
 * @Author    : JUNG YEON HO
 * @Since     : 2020. 3. 6. 오전 10:54:30
 * @Version   : 1.0
 * Copyright (c) ETOOS.
 * -------------------------------------------------------------------
 * Modification Information
 * -------------------------------------------------------------------
 * 수정일                                                     수정자                                         수정 내용
 * -------------------------------------------------------------------
 * 2020. 3. 6.                    JUNG YEON HO              [ETOOS] 최초 생성
 */
public class ExtStringUtils extends StringUtils {

    public static boolean isEmpty(Object str) {
        return (str == null || EMPTY.equals(str));
    }

    public static String defaultString(Object str, String defaultStr) {
        return str == null ? defaultStr : EMPTY.equals(str) ? defaultStr : str.toString();
    }

    public static String getStatckTrace(StackTraceElement[] stackTrace) {
        StackTraceElement[] ste = stackTrace;
        String trace = "\nCaused by: \n";
        for (int i = 0; i < ste.length; i++) {
            trace += "\tat " + ste[i].toString() + "\n";
        }

        return trace;
    }

    /**
     * <pre>
     * 설    명 : 전체 문자열에서 open 과 close 사이에 있는 문자열 중 시작위치에 addStr 를 추가하여
     *            전체 문자열을 반환한다.
     *
     * str = UPDATE BOARD SET AA=:aa, BB=:bb WHERE BRD_NO = 1
     * EcmStringUtils.betweenStartAdd(str, "SET", "WHERE", "SYST_CHNG_DT = SYSDATE, ");
     * return : UPDATE BOARD SET SYST_CHNG_DT = SYSDATE, AA=:aa, BB=:bb WHERE BRD_NO = 1
     * </pre>
     *
     * @param str
     *            문자열
     * @param open
     *            시작문자열
     * @param close
     *            마지막문자열
     * @param addStr
     *            추가문자열
     * @return String 변경된 문자열
     *
     * @since 2010. 02. 04
     * @author Ham Jangsu
     */
    public static String betweenStartAdd(String str, String open, String close, String addStr) {

        if (str == null || open == null || close == null) {
            return null;
        }

        String tempStr = str.toUpperCase();
        String tempOpen = open.toUpperCase();
        String tempClose = close.toUpperCase();
        int start = tempStr.indexOf(tempOpen);

        if (start != -1) {
            int end = tempStr.indexOf(tempClose, start + tempOpen.length());
            if (end != -1) {
                return str.substring(0, start + open.length()) + addStr + str.substring(start + open.length(), end) + str.substring(end, str.length());
            }
        }

        return null;
    }

    /**
     * <pre>
     * 설    명 : 전체 문자열에서 open 과 close 사이에 있는 문자열 중 마지막위치에 addStr 를 추가하여
     *            전체 문자열을 반환한다.
     *
     * str = UPDATE BOARD SET AA=:aa, BB=:bb WHERE BRD_NO = 1
     * EcmStringUtils.betweenEndAdd(str, "SET", "WHERE", ", SYST_CHNG_DT = SYSDATE");
     * return : UPDATE BOARD SET AA=:aa, BB=:bb, SYST_CHNG_DT = SYSDATE,  WHERE BRD_NO = 1
     * </pre>
     *
     * @param str
     *            문자열
     * @param open
     *            시작문자열
     * @param close
     *            마지막문자열
     * @param addStr
     *            추가문자열
     * @return String 변경된 문자열
     *
     * @since 2010. 02. 04
     * @author Ham Jangsu
     */
    public static String betweenEndAdd(String str, String open, String close, String addStr) {

        if (str == null || open == null || close == null) {
            return null;
        }

        String tempStr = str.toUpperCase();
        String tempOpen = open.toUpperCase();
        String tempClose = close.toUpperCase();

        int start = tempStr.indexOf(tempOpen);
        if (start != -1) {
            int end = tempStr.indexOf(tempClose, start + tempOpen.length());
            if (end != -1) {
                return str.substring(0, start + open.length()) + str.substring(start + open.length(), end) + addStr + str.substring(end, str.length());
            }
        }

        return null;
    }

    /**
     * <pre>
     * 설    명 : 전체 문자열에서 open 과 close 사이에 있는 문자열(배열) 중 시작위치에 addStr 를 추가하여
     *            전체 문자열을 반환한다.
     *
     * addStrArray = {"SYST_RGST_DT, ","SYSDATE, "};
     * str = INSERT INTO BOARD (AA, BB, CC) VALUES (:aa, :bb, :cc)
     * EcmStringUtils.betweenStartAdds(str, "(", ")", addStrArray);
     * return : INSERT INTO BOARD (SYST_RGST_DT, AA, BB, CC) VALUES (SYSDATE, :aa, :bb, :cc)
     * </pre>
     *
     * @param str
     *            문자열
     * @param open
     *            시작문자열
     * @param close
     *            마지막문자열
     * @param addStr
     *            [] 추가할 문자열 배열
     * @return String 변경된 문자열
     *
     * @since 2010. 02. 04
     * @author Ham Jangsu
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String betweenStartAdds(String str, String open, String close, String[] addStrArray) {

        if (str == null || isEmpty(open) || isEmpty(close)) {
            return null;
        }

        int strLen = str.length();

        if (strLen == 0) {
            return "";
        }

        int closeLen = close.length();
        int openLen = open.length();
        List list = new ArrayList();
        int pos = 0;
        int i = 0;

        String tempStr = str.toUpperCase();
        String tempOpen = open.toUpperCase();
        String tempClose = close.toUpperCase();

        while (pos < (strLen - closeLen)) {
            int start = tempStr.indexOf(tempOpen, pos);
            if (start < 0) {
                break;
            }

            start += openLen;

            int end = tempStr.indexOf(tempClose, start);
            if (end < 0) {
                break;
            }

            list.add(str.substring(pos, start) + addStrArray[i] + str.substring(start, end) + str.substring(end, end + 1));
            pos = end + closeLen;
            ++i;
        }

        if (list.isEmpty()) {
            return null;
        }

        String newStr = "";
        for (int j = 0; j < list.size(); j++) {
            newStr += list.get(j);
        }

        return newStr;
    }

    /**
     * <pre>
     * 설    명 : 전체 문자열에서 open 과 close 사이에 있는 문자열(배열) 중 마지막위치에 addStr 를 추가하여
     *            전체 문자열을 반환한다.
     *
     * addStrArray = {", SYST_RGST_DT",", SYSDATE"};
     * str = INSERT INTO BOARD (AA, BB, CC) VALUES (:aa, :bb, :cc)
     * EcmStringUtils.betweenStartAdds(str, "(", ")", addStrArray);
     * return : INSERT INTO BOARD (AA, BB, CC, SYST_RGST_DT) VALUES (:aa, :bb, :cc, SYSDATE)
     * </pre>
     *
     * @param str
     *            문자열
     * @param open
     *            시작문자열
     * @param close
     *            마지막문자열
     * @param addStr
     *            [] 추가할 문자열 배열
     * @return String 변경된 문자열
     *
     * @since 2010. 02. 04
     * @author Ham Jangsu
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String betweenEndAdds(String str, String open, String close, String[] addStrArray) {

        if (str == null || isEmpty(open) || isEmpty(close)) {
            return null;
        }

        int strLen = str.length();
        if (strLen == 0) {
            return "";
        }

        int closeLen = close.length();
        int openLen = open.length();
        List list = new ArrayList();
        int pos = 0;
        int i = 0;

        String tempStr = str.toUpperCase();
        String tempOpen = open.toUpperCase();
        String tempClose = close.toUpperCase();

        while (pos < (strLen - closeLen)) {
            int start = tempStr.indexOf(tempOpen, pos);
            if (start < 0) {
                break;
            }
            start += openLen;
            int end = tempStr.indexOf(tempClose, start);
            if (end < 0) {
                break;
            }
            list.add(str.substring(pos, start) + str.substring(start, end) + addStrArray[i] + str.substring(end, end + 1));
            pos = end + closeLen;
            ++i;
        }

        if (list.isEmpty()) {
            return null;
        }

        String newStr = "";
        for (int j = 0; j < list.size(); j++) {
            newStr += list.get(j);
        }

        return newStr;
    }

    /**
     * <pre>
     * 설    명 : 대상문자열(strTarget)에서 구분문자열(strDelim)을 기준으로 문자열을 분리하여
     *            각 분리된 문자열을 배열에 할당하여 반환한다
     * </pre>
     *
     * @param strTarget
     * @param strDelim
     * @param bContainNull
     * @return
     *
     * @since 2010. 02. 04
     * @author |$ユン™|
     */
    public static String[] split(String strTarget, String strDelim, boolean bContainNull) {

        // StringTokenizer는 구분자가 연속으로 중첩되어 있을 경우 공백 문자열을 반환하지 않음.
        // 따라서 아래와 같이 작성함.

        int index = 0;
        String[] resultStrArray = new String[search(strTarget, strDelim) + 1];
        String strCheck = new String(strTarget);

        while (strCheck.length() != 0) {
            int begin = strCheck.indexOf(strDelim);
            if (begin == -1) {
                resultStrArray[index] = strCheck;
                break;
            } else {
                int end = begin + strDelim.length();
                if (bContainNull) {
                    resultStrArray[index++] = strCheck.substring(0, begin);
                }
                strCheck = strCheck.substring(end);
                if (strCheck.length() == 0 && bContainNull) {
                    resultStrArray[index] = strCheck;
                    break;
                }
            }
        }

        return resultStrArray;
    }

    /**
     * <pre>
     * 설    명 : 대상문자열(strTarget)에서 지정문자열(strSearch)이 검색된 횟수를,
     *            지정문자열이 없으면 0 을 반환한다
     * </pre>
     *
     * @param strTarget
     * @param strSearch
     * @return
     *
     * @since 2010. 02. 04
     * @author |$ユン™|
     */
    public static int search(String strTarget, String strSearch) {

        int result = 0;
        String strCheck = new String(strTarget);

        for (int i = 0; i < strTarget.length();) {
            int loc = strCheck.indexOf(strSearch);
            if (loc == -1) {
                break;
            } else {
                result++;
                i = loc + strSearch.length();
                strCheck = strCheck.substring(i);
            }
        }

        return result;
    }

    /**
     * <pre>
     * 설    명 : MD5 로 암호화하여 리턴한다.
     * </pre>
     *
     * @param 입력
     *            문자열
     * @return
     *
     * @since 2010. 10. 25
     * @author Ham Jangsu
     */
    public static String getMD5String(String inputValue) throws Exception {

        byte[] digest = MessageDigest.getInstance("MD5").digest(inputValue.getBytes());
        StringBuffer s = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            s.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
            s.append(Integer.toString(digest[i] & 0x0f, 16));
        }

        return s.toString();
    }

    /**
     * D:\\Development\\workspace\\GCMS\\WebContent\\images
     *
     * @param args
     */
    public static List<String> printImageResource(String contextPath, String filePath, String dirNm) {

        // File 유형의 참조형 변수를 선언합니다. 지역 변수이므로 null 로 초기화합니다.
        File f = null;

        // 전달된 명령행 매개변수가 없으면 현재 디렉토리를 가지고 File 객체를 생성합니다.
        f = new File(filePath);

        List<String> result = new ArrayList<String>();
        result.add("<dl>");
        getImageResource(f, 0, dirNm, contextPath, result);

        return result;
    }

    private static void getImageResource(File dirFile, int depth, String dirNm, String contextPath, List<String> result) {

        // 지정된 디렉토리에 존재하는 파일과 하위 디렉토리에 대한 명칭들을 String 형 배열 객체로 리턴합니다.
        String contents[] = dirFile.list();
        for (int i = 0; i < contents.length; i++) {

            String dd = "";
            // 하위 디렉토리의 Depth 만큼 들여쓰기가 되도록 합니다.
            for (int spaces = 0; spaces < depth; spaces++) {
                dd += dd;
            }

            // 현재 디렉토리와 파일 정보를 가지고 File 객체를 생성합니다.,gif,bmp,tif,png,jpeg
            File child = new File(dirFile, contents[i]);
            if (!child.isDirectory()) {
                int len = contents[i].length() - 3;
                if (contents[i].lastIndexOf("jpg") == len || contents[i].lastIndexOf("gif") == len || contents[i].lastIndexOf("png") == len) {

                    result.add(dd + "<dt><div>" + contextPath + dirNm + "/" + contents[i] + "</div></dt>\n");
                    result.add(dd + "<dd><div><img src=\"" + contextPath + dirNm + "/" + contents[i] + "\"/></div></dd>\n");
                }
            }

            // 현재 디렉토리의 파일이 하위 디렉토리인 경우에만 resurse() 메서드를 재귀 호출합니다.
            if (child.isDirectory()) {
                if (i > 0) {
                    System.out.print(dd);
                }

                if (contents[i].indexOf(".") < 0) {
                    result.add(dd + "<dl>\n");
                    result.add(dd + "<div class='dir'>dir=" + dirNm + "/" + contents[i] + "</div>\n");
                    getImageResource(child, depth + 1, dirNm + "/" + contents[i], contextPath, result);
                }
            }

        }

        result.add("</dl>\n");
        result.add("<hr/>\n");
    }


    /**
     * html escape, /n, ' ' 변환
     *
     * @param String
     * @return String
     * @exception Exception
     */
    public static String toContents(String srcString) throws Exception {

        if (srcString == null || srcString.length() <= 0) {
            return "";
        }

        final StringBuffer result = new StringBuffer();
        final StringCharacterIterator iterator = new StringCharacterIterator(srcString);
        char character = iterator.current();

        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '\"') {
                result.append("&quot;");
            } else if (character == '\'') {
                result.append("&#039;");
            } else if (character == '\\') {
                result.append("&#092;");
            } else if (character == '&') {
                result.append("&amp;");
            } else if (character == '\n') {
                result.append("<br/>");
            } else if (character == ' ') {
                result.append("&nbsp;");
            } else {
                // the char is not a special one
                // add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }

        return result.toString().replaceAll("&amp;#", "&#");
    }

    public static String removeCarriage(String str) throws Exception {
        return str.replaceAll("\r", "").replaceAll("\n", "");
    }

    /**
     * 문자열을 입력된 길이보다 길면 길이만큼 자른후 ...을 붙여서 반환
     *
     * @param String
     *            문자열
     * @param int 문자열길이
     * @return String ...처리된 문자열
     * @exception Exception
     */
    public static String shortString(String str, int len) throws Exception {

        if (str == null || str.length() <= 0) {
            return str;
        }

        if (str.length() <= len) {
            return str;
        } else {
            StringBuffer result = new StringBuffer();
            result.append(str.substring(0, len - 1));
            result.append("...");
            return result.toString();
        }

    }

    /**
     * 문자열에 3자리수마다 콤마 찍기
     *
     * @param String
     *            문자열
     * @return String 3자리수마다 ,찍힌 문자
     * @exception Exception
     */
    public static String moneyString(int str) throws Exception {
        java.text.DecimalFormat decFormat = new java.text.DecimalFormat("###,###,###,###");
        return decFormat.format(str);
    }

    /**
     * 문자열에 3자리수마다 콤마 찍기
     *
     * @param String
     *            문자열
     * @return String 3자리수마다 ,찍힌 문자
     * @exception Exception
     */
    public static String moneyString(String str) throws Exception {
        return moneyString(Integer.parseInt(str));
    }

    /**
     * 문자열에 3자리수마다 콤마 찍기
     *
     * @param String
     *            문자열
     * @return String 3자리수마다 ,찍힌 문자
     * @exception Exception
     */
    public static Long mathRound(Double d) throws Exception {
        return Math.round(d);
    }

    /**
     * 다국어 조회
     *
     */
    public static String lang(Map<String, Object> map, String key, String ds) {
        return ExtStringUtils.defaultString(map.get(key), ds);
    }

    /**
     * 문자열을 0에서부터 자리수만큼 증가하면서 잘라내어 List 형으로 리턴
     *
     * @param String
     *            문자열
     * @return String
     * @exception Exception
     */
    public static List<Map<String, Object>> startToInvervalSubstring(String s, int interval) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int strLength = s.length();
        int divide = strLength / interval;

        for (int i = 1; i <= divide; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("val", s.substring(0, (i * interval)));
            list.add(map);
        }

        return list;
    }

    /**
     * 메세지 조회
     *
     */
    public static String getRequestParameterString(HttpServletRequest req) {

        Enumeration<String> params = req.getParameterNames();
        StringBuilder str = new StringBuilder();

        while (params.hasMoreElements()) {
            String pName = params.nextElement();
            String pValue = req.getParameter(pName);

            if (pName.indexOf("columns[") < 0 && pName.indexOf("search[") < 0 && pName.indexOf("order[") < 0) {
                str.append(pName);
                str.append(":");
                str.append(pValue);
                str.append("\n");
            }

        }

        return str.toString();
    }

    /**
     * 메세지 조회
     *
     */
    public static String replaceLike(Object p, String likePosition) {
        return replaceLike(p, likePosition, "");
    }

    /**
     * 메세지 조회
     *
     */
    public static String replaceLike(Object params, String likePosition, String strCase) {

        String s = "";
        String p = ExtStringUtils.defaultString(params, "");

        if (!p.toString().equals("")) {
            if (likePosition.equals("L")) {
                s = "%" + p.toString().replace("_", "\\_").replace("%", "\\%");
            } else if (likePosition.equals("R")) {
                s = p.toString().replace("_", "\\_").replace("%", "\\%") + "%";
            } else if (likePosition.equals("B")) {
                s = "%" + p.toString().replace("_", "\\_").replace("%", "\\%") + "%";
            } else {
                s = p.toString().replace("_", "\\_").replace("%", "\\%");
            }

            if (strCase.equals("U")) {
                s = s.toUpperCase();
            } else if (strCase.equals("L")) {
                s = s.toLowerCase();
            }

        }

        return s;
    }

    /**
     * String[] 문자열에서 ArrayIndexOutOfBoundsException 발생할때 기본값으로 대체하는 기능
     *
     */
    public static String defaultStringArray(String[] str, int index, String defaultString) {

        try {
            if (str != null) {
                return str[index];
            } else {
                return defaultString;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return defaultString;
        }

    }

    /**
     * 개요 : 상품 결제 여부
     * @history :
     * @Auther : JungYeonHo
     * @Method : getGoodYn
     * @Date : 2018-11-07
     * @param goodsList
     * @return
     */
    public static String getGoodYn(List<Map<String, Object>> goodsList) {
        String goodYn = "N";
        // 결제여부
        if (goodsList != null && goodsList.size() > 0) {
            for (Map<String, Object> info : goodsList) {
                if ("B23891".equals(String.valueOf(info.get("goods_id")))) {
                    goodYn = "Y";
                    break;
                }
            }
        }
        return goodYn;
    }

    /**
     * 개요 :
     * @history :
     * @Auther : JungYeonHo
     * @Method : empty
     * @Date : 2019-07-10
     * @param obj
     * @return
     */
    public static Boolean empty(Object obj) {
        if (obj instanceof String)
            return obj == null || "".equals(obj.toString().trim());
        else if (obj instanceof List)
            return obj == null || ((List<?>) obj).isEmpty();
        else if (obj instanceof Map)
            return obj == null || ((Map<?, ?>) obj).isEmpty();
        else if (obj instanceof Object[])
            return obj == null || Array.getLength(obj) == 0;
        else
            return obj == null;
    }

    /**
     * 개요 :
     * @history :
     * @Auther : JungYeonHo
     * @Method : notEmpty
     * @Date : 2019-07-10
     * @param obj
     * @return
     */
    public static Boolean notEmpty(Object obj) {
        return !empty(obj);
    }
}
