package com.chinaums.fapiao.demo.invoiceapi;

import com.alibaba.fastjson.JSONObject;
import com.chinaums.fapiao.demo.util.HttpClient;
import com.chinaums.fapiao.demo.util.SignUtil;
import com.chinaums.fapiao.demo.util.UUIDGenerator;

import java.util.Calendar;

/**
 * 发票状态查询
 */
public class TestInventoryQuery {

    public static String getRequestJson() {
        JSONObject req = new JSONObject();

        req.put("msgType", "inventory.query");
        req.put("msgId", UUIDGenerator.getUUID());
        req.put("msgSrc", "PUBLIC_TEST");
        req.put("requestTimestamp", String.format("%1$tF %1$tT",Calendar.getInstance().getTimeInMillis()));
        req.put("srcReserve", "发票库存查询");

        req.put("merchantId", "898009990000010");
        req.put("terminalId", "00000001");

        String key = "c8a9236183cb47ab82bb2fab2df0a13a";
        String sign = SignUtil.signWithSha(req, key, "utf-8");
        req.put("sign", sign);

        return req.toString();
    }

    public static void main(String[] args) {

        String url = "https://mobl-test.chinaums.com/fapiao-api-test/";
        String req = getRequestJson();
        System.out.println("req : " + req);
        HttpClient httpClient = new HttpClient();
        String resp = httpClient.doPost(url, req);
        System.out.println("\r\nresp: " + resp);

        /*List<String> str = new ArrayList<>();
        str.add("1234ABC");
        str.add("cc");
        JSONObject object1 = new JSONObject();
        object1.put("vcccc", JSON.toJSON(str));
        System.out.print(JSON.toJSONString(object1));

        String[] str1 = {"ccc"};
        System.out.print(JSON.toJSONString(str1));*/
    }

}
