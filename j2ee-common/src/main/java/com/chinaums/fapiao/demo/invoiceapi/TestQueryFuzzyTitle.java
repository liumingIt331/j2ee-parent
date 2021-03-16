package com.chinaums.fapiao.demo.invoiceapi;

import java.util.Calendar;

import com.alibaba.fastjson.JSONObject;
import com.chinaums.fapiao.demo.util.HttpClient;
import com.chinaums.fapiao.demo.util.SignUtil;
import com.chinaums.fapiao.demo.util.UUIDGenerator;

/**
 * 发票抬头模糊查询
 */
public class TestQueryFuzzyTitle {

    public static String getRequestJson() {
        JSONObject req = new JSONObject();

        req.put("msgType", "query.fuzzy.title");
        req.put("msgId", UUIDGenerator.getUUID());
        req.put("msgSrc", "PUBLIC_TEST");
        req.put("requestTimestamp", String.format("%1$tF %1$tT",Calendar.getInstance().getTimeInMillis()));
        req.put("srcReserve", "发票抬头模糊查询");

        req.put("name", "小米");

        String key = "c8a9236183cb47ab82bb2fab2df0a13a";
        String sign = SignUtil.signWithSha(req, key, "utf-8");
        req.put("sign", sign);

        return req.toString();
    }

    public static void main(String[] args) {

        String url = "https://mobl-test.chinaums.com/fapiao-api-test/";

        String req = getRequestJson();

        System.out.println("req: " + req);

        HttpClient httpClient = new HttpClient();

        String resp = httpClient.doPost(url, req);

        System.out.println("\r\nresp: " + resp);
    }

}
