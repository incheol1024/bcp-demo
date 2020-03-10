package com.etoos.common.util.lang;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <pre>
 * com.etoos.common.util.lang
 *   |_ ExtCookieUtil.java
 * </pre>
 *
 * 1. 업무명         :
 * 2. 단위 업무명  :
 * @Class     : ExtCookieUtil
 * @Author    : JUNG YEON HO
 * @Since     : 2020. 3. 6. 오전 10:50:58
 * @Version   : 1.0
 * Copyright (c) ETOOS.
 * -------------------------------------------------------------------
 * Modification Information
 * -------------------------------------------------------------------
 * 수정일                                                     수정자                                         수정 내용
 * -------------------------------------------------------------------
 * 2020. 3. 6.                    JUNG YEON HO              [ETOOS] 최초 생성
 */
public class ExtCookieUtil {

    /**
     * Sets the cookie.
     *
     * @param response the response
     * @param cookieNm the cookie nm
     * @param cookieVal the cookie val
     * @param domain the domain
     * @param isSecure the is secure
     * @param isHttpOnly the is http only
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain) throws UnsupportedEncodingException {

        // 특정의 encode 방식을 사용해 캐릭터 라인을 application/x-www-form-urlencoded 형식으로 변환
        // 일반 문자열을 웹에서 통용되는 'x-www-form-urlencoded' 형식으로 변환하는 역할
        String cookieValueEnc = URLEncoder.encode(cookieValue, "utf-8");

        // 쿠키생성 - 쿠키의 이름, 쿠키의 값
        Cookie cookie = new Cookie(cookieName, cookieValueEnc);
        cookie.setDomain(domain);
        cookie.setSecure(true);
        // 쿠키의 유효시간 설정
        cookie.setMaxAge(-1);

        //0 if the cookie should comply with the original Netscape specification; 1 if the cookie should comply with RFC 2109
        //cookie.setVersion(1);

        //Specifies a comment that describes a cookie's purpose
        //cookie.setComment(purpose);
        // response 내장 객체를 이용해 쿠키를 전송
        response.addCookie(cookie);
    }


    /**
     * Gets the cookie.
     *
     * @param request the request
     * @param cookieNm the cookie nm
     * @return the cookie
     * @throws Exception the exception
     */
    public static String getCookie(HttpServletRequest request, String cookieNm) throws Exception {

        // 한 도메인에서 여러 개의 쿠키를 사용할 수 있기 때문에 Cookie[] 배열이 반환
        // Cookie를 읽어서 Cookie 배열로 반환
        Cookie[] cookies = request.getCookies();
        String cookieValue="";
        if (cookies != null){
            cookieValue = null;

            // 입력받은 쿠키명으로 비교해서 쿠키값을 얻어낸다.
            for (int i = 0; i < cookies.length; i++) {
                String key = URLDecoder.decode(cookies[i].getName(), "utf-8").toUpperCase();
                if (cookieNm.toUpperCase().equals(key)) {
                    // 특별한 encode 방식을 사용해 application/x-www-form-urlencoded 캐릭터 라인을 디코드
                    // URLEncoder로 인코딩된 결과를 디코딩하는 클래스
                    cookieValue = URLDecoder.decode(cookies[i].getValue(), "euc-kr");
                    break;
                }
            }
        }

        return cookieValue;
    }


    /**
     * Sets the cookie.
     *
     * @param response the response
     * @param cookieNm the cookie nm
     * @param domain the domain
     * @param isSecure the is secure
     * @param isHttpOnly the is http only
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static void setCookie(HttpServletResponse response, String cookieName,String domain) throws UnsupportedEncodingException {
        // 쿠키생성 - 쿠키의 이름, 쿠키의 값
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setDomain(domain);
        cookie.setSecure(true);
        // 쿠키를 삭제하는 메소드가 따로 존재하지 않음
        // 쿠키의 유효시간을 0으로 설정해 줌으로써 쿠키를 삭제하는 것과 동일한 효과
        cookie.setMaxAge(0);
        // response 내장 객체를 이용해 쿠키를 전송
        response.addCookie(cookie);
    }
}

