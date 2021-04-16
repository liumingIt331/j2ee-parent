package com.liuming.mej2ee.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/8
 * Time: 10:40
 * To change this template use File | Settings | File Templates.
 * Description:
 */

public class TestUtil {
    public static void main(String[] args) {
//        System.out.println("Hello World!");

        Demo demo = new Demo();
        String string = JSON.toJSONString(demo);
        System.out.println(string);

        ConcurrentHashMap<String, String> map1 = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, String> map2 = new ConcurrentHashMap<>();

        map1.put("3", "3");
        map1.put("4", "4");

        map2.put("4", "4");

        System.out.println(JSON.toJSON(map1));
        System.out.println(JSON.toJSON(map2));


        System.out.println("流： " + new ByteArrayOutputStream());


        JSONObject request = new JSONObject();
        request.put("amount", 108022782.665882);
        System.out.println("amount:" + request.getLong("amount"));

        System.out.println(Double.doubleToLongBits(55.673));
        System.out.println(Double.doubleToLongBits(55.677));
        System.out.println(Double.compare(55.673, 55.677));
    }
}


class Demo {

    public String getApiName() {
        return "testApi";
    }
}