package com.chinaums.fapiao.demo.api;

import com.alibaba.fastjson.JSONObject;
import com.chinaums.fapiao.demo.util.HttpClient;
import com.chinaums.fapiao.demo.util.SignUtil;
import com.chinaums.fapiao.demo.util.UUIDGenerator;

import java.util.Calendar;

/**
 * 发票状态查询
 */
public class TestFinPickup {

    public static String getRequestJson() {
        JSONObject req = new JSONObject();

        req.put("msgType", "finance.pickup");
        req.put("msgId", UUIDGenerator.getUUID());
        req.put("msgSrc", Constant.MSG_SRC);
        req.put("requestTimestamp", String.format("%1$tF %1$tT",Calendar.getInstance().getTimeInMillis()));
        req.put("srcReserve", "财政票据版式文件下载");

        req.put("merchantId", "898004010900041");
        req.put("terminalId", "10900041");
        req.put("bizNo", "c5010a8e970241cb8758c9d32c481544");
        req.put("bizDate", "20210316");
        req.put("reversing", false);
        req.put("needImg", false);

        String sign = SignUtil.signWithSha(req, Constant.MSG_SRC_KEY, "utf-8");
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
