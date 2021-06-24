package com.chinaums.fapiao.demo.api;

import com.alibaba.fastjson.JSONObject;
import com.chinaums.fapiao.demo.util.HttpClient;
import com.chinaums.fapiao.demo.util.SignUtil;
import com.chinaums.fapiao.demo.util.UUIDGenerator;

import java.util.Calendar;

/**
 * 发票状态查询
 */
public class TestFinReverse {

    public static String getRequestJson() {
        JSONObject req = new JSONObject();

        req.put("msgType", "finance.reverse");
        req.put("msgId", UUIDGenerator.getUUID());
        req.put("msgSrc", Constant.MSG_SRC);
        req.put("requestTimestamp", String.format("%1$tF %1$tT",Calendar.getInstance().getTimeInMillis()));
        req.put("srcReserve", "财政票据红冲");

        req.put("merchantId", "898000099000001");
        req.put("terminalId", "00000009");
        req.put("bizNo", "dacd1109df7e45df864dd8faba35afa7");
        req.put("bizDate", "20210511");
        req.put("reverseReason", "测试红冲");
        req.put("operator", "测试人");

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
