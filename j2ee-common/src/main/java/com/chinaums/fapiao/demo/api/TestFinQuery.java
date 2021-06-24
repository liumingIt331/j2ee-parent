package com.chinaums.fapiao.demo.api;

import com.alibaba.fastjson.JSONObject;
import com.chinaums.fapiao.demo.util.HttpClient;
import com.chinaums.fapiao.demo.util.SignUtil;
import com.chinaums.fapiao.demo.util.UUIDGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 发票状态查询
 */
public class TestFinQuery {

    public static String getRequestJson() {
        JSONObject req = new JSONObject();

        req.put("msgType", "finance.query");
        req.put("msgId", UUIDGenerator.getUUID());
        req.put("msgSrc", Constant.MSG_SRC);
        req.put("requestTimestamp", String.format("%1$tF %1$tT",Calendar.getInstance().getTimeInMillis()));
        req.put("srcReserve", "财政票据状态查询");

        req.put("merchantId", "898000099000001");
        req.put("terminalId", "00000009");
        req.put("bizNo", "e24eadedfbda49eeb909b48059ced18b");
        req.put("bizDate", "20210517");

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
