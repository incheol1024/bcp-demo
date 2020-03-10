package com.etoos.common.util.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;



/**
 * <pre>
 * com.etoos.common.util.lang
 *   |_ ExtDateUtils.java
 * </pre>
 *
 * 1. 업무명         :
 * 2. 단위 업무명  :
 * @Class     : ExtDateUtils
 * @Author    : JUNG YEON HO
 * @Since     : 2020. 3. 6. 오전 11:24:00
 * @Version   : 1.0
 * Copyright (c) ETOOS.
 * -------------------------------------------------------------------
 * Modification Information
 * -------------------------------------------------------------------
 * 수정일                                                     수정자                                         수정 내용
 * -------------------------------------------------------------------
 * 2020. 3. 6.                    JUNG YEON HO              [ETOOS] 최초 생성
 */
@Slf4j
public class ExtDateUtils extends org.apache.commons.lang3.time.DateUtils{

    /**
     * 현재 일시에 해당하는 Calendar 객체를 반환함.
     *
     * <pre>
     *  ex) Calendar cal = DateUtil.getCalendarInstance()
     * </pre>
     *
     * @return 결과 calendar객체
     */
    public static Calendar getCalendarInstance() {
        Calendar retCal = Calendar.getInstance();
        return retCal;
    }

    /**
     * 입력한 년, 월, 일에 해당하는 Calendar 객체를 반환함. 일자를 바르게 입력하지않으면 엉뚱한 결과가 나타남..
     *
     * <pre>
     *  ex) Calendar cal = DateUtil.getCalendarInstance(1982, 12, 02)
     * </pre>
     *
     * @param year
     *            년
     * @param month
     *            월
     * @param date
     *            일
     * @return 결과 calendar객체
     */
    public static Calendar getCalendarInstance(int year, int month, int date) {
        Calendar retCal = Calendar.getInstance();
        month--;

        retCal.set(year, month, date);

        return retCal;
    }

    /**
     * 입력한 년, 월, 일, 시, 분, 초에 해당하는 Calendar 객체를 반환함.<br>
     * 일자를 바르게 입력하지않으면 엉뚱한 결과가 나타남..
     *
     * <pre>
     *  ex) Calendar cal = DateUtil.getCalendarInstance(1982, 12, 02, 12, 59, 59)
     * </pre>
     *
     * @param year
     *            년
     * @param month
     *            월
     * @param date
     *            일
     * @param hour
     *            시
     * @param minute
     *            분
     * @param second
     *            초
     * @return 결과 calendar객체
     */
    public static Calendar getCalendarInstance(int year, int month, int date,
            int hour, int minute, int second) {
        Calendar retCal = Calendar.getInstance();
        month--;

        retCal.set(year, month, date, hour, minute, second);

        return retCal;
    }

    /**
     * calendar에 해당하는 일자를 type의 날짜형식으로 반환합니다.<br>
     * 타입의 형식을 반드시 지켜야 합니다.<br>
     * (자세한 사항은 SimpleDateFormat java document 참조.)
     *
     * <pre>
     *  ex) Calendar cal = DateUtil.getCalendarInstance(1982, 12, 02, 12, 59, 59);
     *      DateUtil.getDateFormat(cal, "yyyyMMddHHmmssSSS")
     *      DateUtil.getDateFormat(cal, "yyyy-MM-dd hh:mm:ss")
     *      DateUtil.getDateFormat(cal, "yyyy년MM월dd일 hh시mm분ss초")
     * </pre>
     *
     * @param cal
     *            calender객체
     * @param type
     *            변환타입
     * @return 변환된 문자열
     */
    public static String getDateFormat(Calendar cal, String type) {
        SimpleDateFormat dfmt = new SimpleDateFormat(type);
        return dfmt.format(cal.getTime());
    }

    /**
     * 현재 일자를 입력된 type의 날짜로 반환합니다.<br>타입의 형식을 반드시 지켜야 합니다.<br>
     * (자세한 사항은 SimpleDateFormat java document 참조.)
     *
     * <pre>
     *  ex) DateUtil.getDateFormat("yyyyMMddHHmmssSSS")
     *      DateUtil.getDateFormat("yyyy-MM-dd hh:mm:ss")
     *      DateUtil.getDateFormat("yyyy년MM월dd일 hh시mm분ss초")
     * </pre>
     *
     * @param type
     *            날짜타입
     * @return 결과 문자열
     */
    public static String getDateFormat(String type) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        return sdf.format(date);
    }

    /**
     * Calender에 해당하는 날짜와 시각을 yyyyMMdd 형태로 변환 후 return.
     *
     * <pre>
     *  ex) String date = DateUtil.getYyyymmdd()
     * </pre>
     *
     * @param cal
     *            Calender객체
     * @return 결과 일자
     */
    public static String getYyyymmdd(Calendar cal) {
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMdd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern,
                currentLocale);
        return formatter.format(cal.getTime());
    }

    /**
     * Calender에 해당하는 날짜와 시각을 yyyyMMdd 형태로 변환 후 return.
     *
     * <pre>
     *  ex) String date = DateUtil.getYyyymmdd()
     * </pre>
     *
     * @param cal
     *            Calender객체
     * @return 결과 일자
     */
    public static String getPatternedDt(Calendar cal, String pattern) {
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        SimpleDateFormat formatter = new SimpleDateFormat(pattern,
                currentLocale);
        return formatter.format(cal.getTime());
    }

    /**
     * 현재 날짜와 시각을 yyyyMMddhhmmss 형태로 변환 후 return.
     *
     * <pre>
     *
     *  ex) String date = DateUtil.getCurrentDateTime()
     * </pre>
     *
     * @return 현재 년월일시분초
     */
    public static String getCurrentDateTime() {
        //자바 1.4 버전 현재 비스타에서 동작시 로컬타임을 잘못가져옴
        //표준시에서 9시간 차이나는것 만큼 표시해야 하는데..
        //비스타에서는 표준시로만 나타냅니다.
        //해결방안으로 setproperty를 추가합니다.
        // 2007.10.10 고재선
        System.setProperty("user.timezone", "Asia/Seoul");
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(today);
    }

    /**
     * 현재 시각을 hhmmss 형태로 변환 후 return.
     *
     * <pre>
     *  ex) String date = DateUtil.getCurrentDateTime()
     * </pre>
     *
     * @return 현재 시분초
     */
    public static String getCurrentTime() {
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "HHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern,
                currentLocale);
        return formatter.format(today);

    }

    /**
     * 현재 날짜를 yyyyMMdd 형태로 변환 후 return.
     *
     * <pre>
     *
     *  ex) String date = DateUtil.getCurrentDate()
     * </pre>
     *
     * @return 현재 년월일
     */
    public static String getCurrentDate() {
        return getCurrentDateTime().substring(0, 8);
    }

    /**
     * 년월주로 일자를 구하는 메소드.
     *
     * <pre>
     *  ex) String date = DateUtil.getWeekToDay("200801" , 1, "yyyyMMdd")
     * </pre>
     *
     * @param yyyymm
     *            년월
     * @param week
     *            몇번째 주
     * @param pattern
     *            리턴되는 날짜패턴 (ex:yyyyMMdd)
     * @return 연산된 날짜
     * @see java.util.Calendar
     */
    @SuppressWarnings("static-access")
    public static String getWeekToDay(String yyyymm, int week, String pattern) {

        Calendar cal = Calendar.getInstance(Locale.FRANCE);

        int new_yy = Integer.parseInt(yyyymm.substring(0, 4));
        int new_mm = Integer.parseInt(yyyymm.substring(4, 6));
        int new_dd = 1;

        cal.set(new_yy, new_mm - 1, new_dd);

        // 임시 코드
        if (cal.get(cal.DAY_OF_WEEK) == cal.SUNDAY) {
            week = week - 1;
        }

        cal.add(Calendar.DATE, (week - 1) * 7
                + (cal.getFirstDayOfWeek() - cal.get(Calendar.DAY_OF_WEEK)));

        SimpleDateFormat formatter = new SimpleDateFormat(pattern,
                Locale.FRANCE);

        return formatter.format(cal.getTime());

    }

    /**
     * 지정된 플래그에 따라 calendar에 해당하는 연도 , 월 , 일자를 연산한다.<br>
     * (calendar객체의 데이터는 변하지 않는다.)
     *
     * <pre>
     *  ex) Calendar cal = DateUtil.getCalendarInstance(1982, 12, 02, 12, 59, 59)
     *      String date = DateUtil.getComputeDate(java.util.Calendar.MONTH , -10, cal);
     *      결과 : 19820202
     * </pre>
     *
     * @param field
     *            연산 필드
     * @param amount
     *            더할 수
     * @param cal
     *            연산 대상 calendar객체
     * @return 연산된 날짜
     * @see java.util.Calendar
     */
    public static String getComputeDate(int field, int amount, Calendar cal) {
        Calendar cpCal = (Calendar) cal.clone();
        cpCal.add(field, amount);
        return getYyyymmdd(cpCal);
    }

    /**
     * 지정된 플래그에 따라 현재의 연도 , 월 , 일자를 연산한다.
     *
     * <pre>
     *  ex) DateUtil.getComputeDate(java.util.Calendar.MONTH , 10);
     *      결과 : 19820202
     * </pre>
     *
     * @param field
     *            연산 필드
     * @param amount
     *            더할 수
     * @return 연산된 날짜
     * @see java.util.Calendar
     */
    public static String getComputeDate(int field, int amount) {
        return getComputeDate(field, amount, getCalendarInstance());
    }

    /**
     * 지정된 플래그에 따라 calendar에 해당하는 연도 , 월 , 일자를 연산하여 지정된 포맷으로 출력한다.<br>
     * (calendar객체의 데이터는 변하지 않는다.)
     *
     * <pre>
     *  ex) Calendar cal = DateUtil.getCalendarInstance(1982, 12, 02, 12, 59, 59)
     *      String date = DateUtil.getComputeDate(java.util.Calendar.MONTH , -10, cal);
     *      결과 : 19820202
     * </pre>
     *
     * @param field
     *            연산 필드
     * @param amount
     *            더할 수
     * @param cal
     *            연산 대상 calendar객체
     * @return 연산된 날짜
     * @see java.util.Calendar
     */
    public static String getComputeDate(int field, int amount, Calendar cal, String pattern) {
        Calendar cpCal = (Calendar) cal.clone();
        cpCal.add(field, amount);
        return getPatternedDt(cpCal, pattern);
    }

    /**
     * 지정된 플래그에 따라 현재의 연도 , 월 , 일자를 연산하여 지정된 포맷으로 출력한다.
     *
     * <pre>
     *  ex) DateUtil.getComputeDate(java.util.Calendar.MONTH , 10);
     *      결과 : 19820202
     * </pre>
     *
     * @param field
     *            연산 필드
     * @param amount
     *            더할 수
     * @return 연산된 날짜
     * @see java.util.Calendar
     */
    public static String getComputeDate(int field, int amount, String pattern) {
        return getComputeDate(field, amount, getCalendarInstance(), pattern);
    }

    /**
     * 입력된 일자를 더한 주를 구하여 return한다
     *
     * <pre>
     *  ex) int date = DateUtil.getWeek(DateUtil.getCurrentYyyymmdd() , 0)
     * </pre>
     *
     * @param yyyymmdd
     *            년도별
     * @param addDay
     *            추가일
     * @return 연산된 주
     * @see java.util.Calendar
     */
    public static int getWeek(String yyyymmdd, int addDay) {
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        int new_yy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int new_mm = Integer.parseInt(yyyymmdd.substring(4, 6));
        int new_dd = Integer.parseInt(yyyymmdd.substring(6, 8));

        cal.set(new_yy, new_mm - 1, new_dd);
        cal.add(Calendar.DATE, addDay);

        int week = cal.get(Calendar.DAY_OF_WEEK);
        return week;
    }

    /**
     * 입력된 년월의 마지막 일수를 return 한다.
     *
     * <pre>
     *  ex) int date = DateUtil.getLastDayOfMon(2008 , 1)
     * </pre>
     *
     * @param year
     *            년
     * @param month
     *            월
     * @return 마지막 일수
     * @see java.util.Calendar
     */
    public static int getLastDayOfMon(int year, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

    }// :

    /**
     * 입력된 년월의 마지막 일수를 return한다
     *
     * <pre>
     *  ex) int date = DateUtil.getLastDayOfMon("2008")
     * </pre>
     *
     * @param yyyymm
     *            년월
     * @return 마지막 일수
     */
    public static int getLastDayOfMon(String yyyymm) {

        Calendar cal = Calendar.getInstance();
        int yyyy = Integer.parseInt(yyyymm.substring(0, 4));
        int mm = Integer.parseInt(yyyymm.substring(4)) - 1;

        cal.set(yyyy, mm, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 입력된 날자가 올바른지 확인합니다.
     *
     * <pre>
     *  ex) boolean b = DateUtil.isCorrect("20080101")
     * </pre>
     *
     * @param yyyymmdd
     * @return boolean
     */
    public static boolean isCorrect(String yyyymmdd) {
        boolean flag = false;
        if (yyyymmdd.length() < 8)
            return false;
        try {
            int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
            int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
            int dd = Integer.parseInt(yyyymmdd.substring(6));
            flag = isCorrect(yyyy, mm, dd);
        } catch (Exception ex) {
            return false;
        }
        return flag;
    }

    /**
     * 입력된 날자가 올바른 날자인지 확인합니다.
     *
     * <pre>
     *  ex) boolean b = DateUtil.isCorrect(2008,1,1)
     * </pre>
     *
     * @param yyyy
     * @param mm
     * @param dd
     * @return boolean
     */
    public static boolean isCorrect(int yyyy, int mm, int dd) {
        if (yyyy < 0 || mm < 0 || dd < 0)
            return false;
        if (mm > 12 || dd > 31)
            return false;

        String year = "" + yyyy;
        String month = "00" + mm;
        String year_str = year + month.substring(month.length() - 2);
        int endday = getLastDayOfMon(year_str);

        if (dd > endday)
            return false;

        return true;

    }


    /**
     * 현재의 요일을 구한다.
     *
     * <pre>
     *  ex) int day = DateUtil.getDayOfWeek()
     *      SUNDAY    = 1
     *      MONDAY    = 2
     *      TUESDAY   = 3
     *      WEDNESDAY = 4
     *      THURSDAY  = 5
     *      FRIDAY    = 6
     * </pre>
     *
     * @return 요일
     * @see java.util.Calendar
     */
    public static int getDayOfWeek() {
        Calendar rightNow = Calendar.getInstance();
        int day_of_week = rightNow.get(Calendar.DAY_OF_WEEK);
        return day_of_week;
    }

    /**
     * 현재주가 올해 전체의 몇째주에 해당되는지 계산한다.
     *
     * <pre>
     *  ex) int day = DateUtil.getWeekOfYear()
     * </pre>
     *
     * @return 주
     * @see java.util.Calendar
     */
    public static int getWeekOfYear() {
        Locale LOCALE_COUNTRY = Locale.KOREA;
        Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
        int week_of_year = rightNow.get(Calendar.WEEK_OF_YEAR);
        return week_of_year;
    }

    /**
     * 현재주가 현재월에 몇째주에 해당되는지 계산한다.
     *
     * <pre>
     *  ex) int day = DateUtil.getWeekOfMonth()
     * </pre>
     *
     * @return 주
     * @see java.util.Calendar
     */
    public static int getWeekOfMonth() {
        Locale LOCALE_COUNTRY = Locale.KOREA;
        Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
        int week_of_month = rightNow.get(Calendar.WEEK_OF_MONTH);
        return week_of_month;
    }


    /**
     * 두 날짜간의 날짜수를 반환(윤년을 감안함)
     *
     * <pre>
     *  ex) long date = DateUtil.getDifferDays("20080101", "20080202")
     * </pre>
     *
     * @param startDate
     *            시작 날짜
     * @param endDate
     *            끝 날짜
     * @return 날수
     * @see java.util.GregorianCalendar
     */
    public static long getDifferDays(String startDate, String endDate) {
        GregorianCalendar StartDate = getGregorianCalendar(startDate);
        GregorianCalendar EndDate = getGregorianCalendar(endDate);
        long difer = (EndDate.getTime().getTime() - StartDate.getTime()
                .getTime()) / 86400000;
        return difer;
    }



    /**
     * 두 날짜간의 월수를 반환
     *
     * <pre>
     *  ex) long date = DateUtil.getDifferMonths("20080101", "20080202")
     * </pre>
     *
     * @param startDate
     *            시작 날짜
     * @param endDate
     *            끝 날짜
     * @return 월수
     * @see java.util.GregorianCalendar
     */
    public static int getDifferMonths(String startDate, String endDate) {
        GregorianCalendar StartDate = getGregorianCalendar(startDate);
        GregorianCalendar EndDate = getGregorianCalendar(endDate);

        int startYear = StartDate.get(Calendar.YEAR);
        int startMonth = StartDate.get(Calendar.MONTH);

        int endYear = EndDate.get(Calendar.YEAR);
        int endMonth = EndDate.get(Calendar.MONTH);

        int cntYear = endYear - startYear;
        int cntMonth = endMonth + (cntYear * 12);
        int ret = cntMonth - startMonth;

        return ret;
    }



    /**
     * GregorianCalendar 객체를 반환함.
     *
     * <pre>
     *  ex) Calendar cal = DateUtil.getGregorianCalendar(DateUtil.getCurrentYyyymmdd())
     * </pre>
     *
     * @param yyyymmdd
     *            날짜 인수
     * @return GregorianCalendar
     * @see java.util.Calendar
     * @see java.util.GregorianCalendar
     */

    private static GregorianCalendar getGregorianCalendar(String yyyymmdd) {

        int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
        int dd = Integer.parseInt(yyyymmdd.substring(6));

        GregorianCalendar calendar = new GregorianCalendar(yyyy, mm - 1, dd, 0,
                0, 0);

        return calendar;

    }


    /**
     * 년월일을 입력받아 입력받은 날자가 마지막 날자인지 체크한다.
     *
     * <pre>
     *  ex) boolean isLastDay = DateUtil.isLastDay(1991, 3, 25)
     * </pre>
     *
     * @param year
     *            시작 날짜
     * @param endDate
     *            끝 날짜
     * @return 월수
     * @see java.util.GregorianCalendar
     */
    public static boolean isLastDay(int year, int month, int day) {

        boolean rtnVal = false;
        int lastDay = getLastDayOfMon(year, month);

        if(lastDay == day){
            rtnVal = true;
        }

        return rtnVal;
    }

    public static long getTime(String formatStr, String timeStr) {
        long timeMillis = 0L;
        try {
            SimpleDateFormat dform = new SimpleDateFormat(formatStr);
            timeMillis = dform.parse(timeStr).getTime();
        }
        catch (ParseException e) {
            return 0L;
        }
        return timeMillis;
    }

    public static String toDisplayFormat(String _date){
        if(_date != null & _date.length() == 8 ){
            return _date.substring(0,4) + "-" + _date.substring(4,6) + "-" + _date.substring(6,8);
        }
        else return _date;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void toDisplayFormat(Map map,String key){
        Object value = map.get(key) ;
        if(value != null && value.toString().length() == 8 ){
            map.put(key, ExtDateUtils.toDisplayFormat(value.toString()));
        }
    }


    public static String toDbFormat(String _date){
        return _date.replace("-","");
    }

    /**
     * 개요:두 날자를 입력 받아 현재 날짜와 시간에 포함되는지 체크 (년월일시분초까지 입력)
     * @history
     * @Method : getIsInDuration
     * @param sDate
     * @param eDate
     * @return
     * @throws
     */

    public static boolean getIsInDuration(String sDate, String eDate){
        boolean result = false;
        try {
            String sDateTemp = ExtStringUtils.replace(ExtStringUtils.replace(ExtStringUtils.replace(sDate, "-", ""), ":", ""), " ", "");
            String eDateTemp = ExtStringUtils.replace(ExtStringUtils.replace(ExtStringUtils.replace(eDate, "-", ""), ":", ""), " ", "");
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
            Date nowDate = new Date();
            Date startDate = format.parse(sDateTemp);
            Date endDate = format.parse(eDateTemp);

            if(nowDate.compareTo(startDate) == 0 || nowDate.compareTo(endDate) == 0 || (startDate.before(nowDate) && endDate.after(nowDate))){
                result = true;
            }
        } catch (Exception e) {
            log.debug("Signals that an error has been reached unexpectedly while parsing.");
        }

        return result;
    }
}
