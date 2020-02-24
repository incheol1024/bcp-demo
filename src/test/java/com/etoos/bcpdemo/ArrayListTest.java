package com.etoos.bcpdemo;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayListTest {

    @Test
    public void ArrayListTest() {
        List<Map<String, Object>>  stringObjectList = new ArrayList<>();
        Map<String, Object> map = new HashMap();
        map.put("s1", new Object[]{1,2});
        stringObjectList.add(map);

        System.out.println(stringObjectList);

    }
}
