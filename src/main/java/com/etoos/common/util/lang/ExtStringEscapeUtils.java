package com.etoos.common.util.lang;

/**
 * <pre>
 * com.etoos.common.util.lang
 *   |_ ExtStringEscapeUtils.java
 * </pre>
 *
 * 1. 업무명         :
 * 2. 단위 업무명  :
 * @Class     : ExtStringEscapeUtils
 * @Author    : JUNG YEON HO
 * @Since     : 2020. 3. 6. 오전 11:27:50
 * @Version   : 1.0
 * Copyright (c) ETOOS.
 * -------------------------------------------------------------------
 * Modification Information
 * -------------------------------------------------------------------
 * 수정일                                                     수정자                                         수정 내용
 * -------------------------------------------------------------------
 * 2020. 3. 6.                    JUNG YEON HO              [ETOOS] 최초 생성
 */
public class ExtStringEscapeUtils extends org.apache.commons.lang3.StringEscapeUtils {

    /**
     * 쿼리 예약어 제거
     *
     * @param stackTrace
     * @return
     */
    public static String replaceSQLInjection(String parameter) {

        String s = parameter.toLowerCase();

        s = s.replace("'", "''");
        s = s.replace("\"", "\"\"");
        s = s.replace(";", "");
        s = s.replace("--", "");
        s = s.replace("select", "");
        s = s.replace("insert", "");
        s = s.replace("delete", "");
        s = s.replace("update", "");
        s = s.replace("from", "");
        s = s.replace("where", "");
        s = s.replace("and", "");
        s = s.replace("or", "");
        s = s.replace("union", "");
        s = s.replace("decalre", "");
        s = s.replace("if", "");
        s = s.replace("drop", "");
        s = s.replace("column_name", "");
        s = s.replace("table_name", "");
        s = s.replace("&&", "");
        s = s.replace("||", "");
        s = s.replace("%", "");
        s = s.replace("#", "");
        s = s.replace("subtring", "");
        s = s.replace("substr", "");
        s = s.replace("sysobjects", "");
        s = s.replace("openrowset", "");
        s = s.replace("syscolumns", "");
        s = s.replace("xp_", "");
        s = s.replace("+", "");
        s = s.replace("=", "");
        s = s.replace("<", "");
        s = s.replace(">", "");
        s = s.replace("(", "");
        s = s.replace(")", "");
        s = s.replace("/*", "");
        s = s.replace("*/", "");

        return s;
    }

    /*
     * XSS 방지
     */
    public static String removeXSS(String str, boolean use_html) {
        String str_low = "";
        if (use_html) { // HTML tag를 사용하게 할 경우 부분 허용
            // HTML tag를 모두 제거
            //	        str = str.replaceAll("<","&lt;");
            //	        str = str.replaceAll(">","&gt;");
            //
            //	        // 특수 문자 제거
            //	        str = str.replaceAll("\"","&gt;");
            //	        str = str.replaceAll("&", "&amp;");
            //	        str = str.replaceAll("%00", null);
            //	        str = str.replaceAll("\"", "&#34;");
            //	        str = str.replaceAll("\'", "&#39;");
            //	        str = str.replaceAll("%", "&#37;");
            //	        str = str.replaceAll("../", "");
            //	        str = str.replaceAll("..\\\\", "");
            //	        str = str.replaceAll("./", "");
            //	        str = str.replaceAll("%2F", "");
            //	        // 허용할 HTML tag만 변경
            //	        str = str.replaceAll("&lt;p&gt;", "<p>");
            //	        str = str.replaceAll("&lt;P&gt;", "<P>");
            //	        str = str.replaceAll("&lt;br&gt;", "<br>");
            //	        str = str.replaceAll("&lt;BR&gt;", "<BR>");

            // 스크립트 문자열 필터링 (선별함 - 필요한 경우 보안가이드에 첨부된 구문 추가)
            str_low = str.toLowerCase();
            if (str_low.contains("javascript") || str_low.contains("script") || str_low.contains("iframe") || str_low.contains("document") || str_low.contains("vbscript") || str_low.contains("applet") || str_low.contains("embed") || str_low.contains("object") || str_low.contains("frame") || str_low.contains("grameset") || str_low.contains("layer") || str_low.contains("bgsound") || str_low.contains("alert") || str_low.contains("onblur") || str_low.contains("onchange") || str_low.contains("onclick") || str_low.contains("ondblclick") || str_low.contains("enerror") || str_low.contains("onfocus") || str_low.contains("onload") || str_low.contains("onmouse") || str_low.contains("onscroll") || str_low.contains("onsubmit") || str_low.contains("onunload")) {
                str = str_low;
                str = str.replaceAll("javascript", "x-javascript");
                str = str.replaceAll("script", "x-script");
                str = str.replaceAll("iframe", "x-iframe");
                str = str.replaceAll("document", "x-document");
                str = str.replaceAll("vbscript", "x-vbscript");
                str = str.replaceAll("applet", "x-applet");
                str = str.replaceAll("embed", "x-embed");
                str = str.replaceAll("object", "x-object");
                str = str.replaceAll("frame", "x-frame");
                str = str.replaceAll("grameset", "x-grameset");
                str = str.replaceAll("layer", "x-layer");
                str = str.replaceAll("bgsound", "x-bgsound");
                str = str.replaceAll("alert", "x-alert");
                str = str.replaceAll("onblur", "x-onblur");
                str = str.replaceAll("onchange", "x-onchange");
                str = str.replaceAll("onclick", "x-onclick");
                str = str.replaceAll("ondblclick", "x-ondblclick");
                str = str.replaceAll("enerror", "x-enerror");
                str = str.replaceAll("onfocus", "x-onfocus");
                str = str.replaceAll("onload", "x-onload");
                str = str.replaceAll("onmouse", "x-onmouse");
                str = str.replaceAll("onscroll", "x-onscroll");
                str = str.replaceAll("onsubmit", "x-onsubmit");
                str = str.replaceAll("onunload", "x-onunload");
            }
        } else { // HTML tag를 사용하지 못하게 할 경우
            str = str.replaceAll("\"", "&gt;");
            str = str.replaceAll("&", "&amp;");
            str = str.replaceAll("<", "&lt;");
            str = str.replaceAll(">", "&gt;");
            str = str.replaceAll("%00", null);
            str = str.replaceAll("\"", "&#34;");
            str = str.replaceAll("\'", "&#39;");
            str = str.replaceAll("%", "&#37;");
            str = str.replaceAll("../", "");
            str = str.replaceAll("..\\\\", "");
            str = str.replaceAll("./", "");
            str = str.replaceAll("%2F", "");
        }
        return str;
    }
}
