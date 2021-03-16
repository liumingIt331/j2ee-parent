package com.chinaums.fapiao.demo.invoiceapi;

import com.alibaba.fastjson.JSONObject;
import com.chinaums.fapiao.demo.util.HttpClient;
import com.chinaums.fapiao.demo.util.SignUtil;
import com.chinaums.fapiao.demo.util.UUIDGenerator;


/**
 * 微信发票抬头识别
 */
public class TestScanWeixinTitle {

    public static String getRequestJson() {
        JSONObject req = new JSONObject();

        req.put("msgType", "scan.weixin.title");
        req.put("msgId", UUIDGenerator.getUUID());
        req.put("msgSrc", "PUBLIC_TEST");
        req.put("requestTimestamp", String.format("%1$tF %1$tT",System.currentTimeMillis()));
        req.put("srcReserve", "微信发票抬头识别");

        req.put("qrCode", "http://w.url.cn/s/AtyrsZ3");

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
