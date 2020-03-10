/*
 * Copyright (c) ETOOS.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of ETOOS.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with ETOOS.
 */
package com.etoos.common.util.lang;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.etoos.common.util.lang
 *   |_ DataKeySetUtil.java
 * </pre>
 *
 * 1. 업무명         :
 * 2. 단위 업무명  :
 * @Class     : DataKeySetUtil
 * @Author    : JUNG YEON HO
 * @Since     : 2020. 3. 6. 오전 10:51:11
 * @Version   : 1.0
 * Copyright (c) ETOOS.
 * -------------------------------------------------------------------
 * Modification Information
 * -------------------------------------------------------------------
 * 수정일                                                     수정자                                         수정 내용
 * -------------------------------------------------------------------
 * 2020. 3. 6.                    JUNG YEON HO              [ETOOS] 최초 생성
 */
public class DataKeySetUtil {
    private DataKeySetUtil() {
        throw new IllegalStateException("DataKeySetUtil class");
    }
    /***********************************************************************
     * Key 값의 대문자를 소문자로 변경
     *
     * @param map
     * @return list
     ***********************************************************************/
    public static List keyChangeLowerList(List<Map> list) {
        List<HashMap> newList = new LinkedList<HashMap>();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Integer> tm = new HashMap<String, Integer>(list.get(i));
            Iterator<String> iteratorKey = tm.keySet().iterator(); // 키값 오름차순
            HashMap newMap = new HashMap();
            // //키값 내림차순 정렬
            while (iteratorKey.hasNext()) {
                String key = iteratorKey.next();
                newMap.put(key.toLowerCase(), tm.get(key));
            }
            newList.add(newMap);
        }
        return newList;
    }

    /***********************************************************************
     * Key 값의 대문자를 소문자로 변경
     *
     * @param map
     * @return map
     ***********************************************************************/
    public static HashMap keyChangeLowerMap(Map param) {
        HashMap newMap = new HashMap();
        if(param != null) {
            Iterator<String> iteratorKey = param.keySet().iterator(); // 키값 오름차순
            // //키값 내림차순 정렬
            while (iteratorKey.hasNext()) {
                String key = iteratorKey.next();
                newMap.put(key.toLowerCase(), param.get(key));
            }
        }
        return newMap;
    }

    /***********************************************************************
     * Key 값의 대문자를 소문자로 변경
     *
     * @param map
     * @return list
     ***********************************************************************/
    public static List keyChangeUpperList(List<Map> list) {
        List<HashMap> newList = new LinkedList<HashMap>();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Integer> tm = new HashMap<String, Integer>(list.get(i));
            Iterator<String> iteratorKey = tm.keySet().iterator(); // 키값 오름차순
            HashMap newMap = new HashMap();
            // //키값 내림차순 정렬
            while (iteratorKey.hasNext()) {
                String key = iteratorKey.next();
                newMap.put(key.toUpperCase(), tm.get(key));
            }
            newList.add(newMap);
        }
        return newList;
    }

    /***********************************************************************
     * Key 값의 대문자를 소문자로 변경
     *
     * @param map
     * @return map
     ***********************************************************************/
    public static HashMap keyChangeUpperMap(Map param) {
        HashMap newMap = new HashMap();
        if(param != null) {
            Iterator<String> iteratorKey = param.keySet().iterator(); // 키값 오름차순
            // //키값 내림차순 정렬
            while (iteratorKey.hasNext()) {
                String key = iteratorKey.next();
                newMap.put(key.toUpperCase(), param.get(key));
            }
        }
        return newMap;
    }

    /***********************************************************************
     * key를 size_cd_test => sizeCdTest 으로 문자열 변경.
     *
     * @param map
     * @return
     ***********************************************************************/
    // HashMap 으로 리턴
    public static HashMap keyConvStyleClient(HashMap map) {
        Iterator<String> iterKey = map.keySet().iterator();
        HashMap newMap = new HashMap();
        while (iterKey.hasNext()) {
            String key = iterKey.next();
            String typeName = map.get(key).getClass().getSimpleName();
            if (typeName.indexOf("List") >= 0) { // 리스트로 간주.
                List<Map> newList = new LinkedList<Map>();
                List subList = (List) map.get(key);
                for (int i = 0; i < subList.size(); i++) {
                    Map subItem = (Map) subList.get(i);
                    newList.add(keyConvStyleClient(subItem));
                }
                newMap.put(convertVariableRevert(key), newList);
            } else {
                newMap.put(convertVariableRevert(key), map.get(key));
            }
        }
        return newMap;
    }

    // Map 으로 리턴
    public static Map keyConvStyleClient(Map map) {
        Iterator<String> iterKey = map.keySet().iterator();
        Map newMap = new HashMap();
        while (iterKey.hasNext()) {
            String key = iterKey.next();
            String typeName = map.get(key).getClass().getSimpleName();
            if (typeName.indexOf("List") >= 0) { // 리스트로 간주.
                List<Map> newList = new LinkedList<Map>();
                List subList = (List) map.get(key);
                for (int i = 0; i < subList.size(); i++) {
                    Map subItem = (Map) subList.get(i);
                    newList.add(keyConvStyleClient(subItem));
                }
                newMap.put(convertVariableRevert(key), newList);
            } else {
                newMap.put(convertVariableRevert(key), map.get(key));
            }
        }
        return newMap;
    }

    /***********************************************************************
     * key를 sizeCdTest => size_cd_test 으로 변경하여 HashMap 구성.
     *
     * @param map
     * @return
     ***********************************************************************/
    // HashMap 리턴
    public static HashMap keyConvStyleDB(HashMap map) {
        Iterator<String> iterKey = map.keySet().iterator();
        HashMap newMap = new HashMap();
        while (iterKey.hasNext()) {
            String key = iterKey.next();
            String typeName = map.get(key).getClass().getSimpleName();
            if (typeName.indexOf("List") >= 0) { // 리스트로 간주.
                List<Map> newList = new LinkedList<Map>();
                List subList = (List) map.get(key);
                for (int i = 0; i < subList.size(); i++) {
                    Map subItem = (Map) subList.get(i);
                    newList.add(keyConvStyleDB(subItem));
                }
                newMap.put(convertVariable(key), newList);
            } else {
                newMap.put(convertVariable(key), map.get(key));
            }
        }
        return newMap;
    }

    // Map 으로 리턴
    public static Map keyConvStyleDB(Map map) {
        Iterator<String> iterKey = map.keySet().iterator();
        Map newMap = new HashMap();
        while (iterKey.hasNext()) {
            String key = iterKey.next();
            String typeName = map.get(key).getClass().getSimpleName();
            if (typeName.indexOf("List") >= 0) { // 리스트로 간주.
                List<Map> newList = new LinkedList<Map>();
                List subList = (List) map.get(key);
                for (int i = 0; i < subList.size(); i++) {
                    Map subItem = (Map) subList.get(i);
                    newList.add(keyConvStyleDB(subItem));
                }
                newMap.put(convertVariable(key), newList);
            } else {
                newMap.put(convertVariable(key), map.get(key));
            }
        }
        return newMap;
    }

    /**
     * key를 size_cd_test => sizeCdTest 으로 문자열 변경.
     */
    static String convertVariableRevert(String key) {
        StringBuffer sb = new StringBuffer(); // 새로 생성
        for (int i = 0; i < key.length(); i++) {
            String str = String.valueOf(key.charAt(i));
            if (str.equals("_")) {
                sb.append(String.valueOf(key.charAt(i + 1)).toUpperCase());
                i++;
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * key를 sizeCdTest => size_cd_test 으로 문자열 변경.
     */
    static String convertVariable(String key) {
        StringBuffer sb = new StringBuffer(); // 새로 생성
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (Character.isUpperCase(ch) == true) {
                sb.append("_" + String.valueOf(ch).toLowerCase());
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
