package com.chinaums.fapiao.demo.invoiceapi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinaums.fapiao.demo.util.HttpClient;
import com.chinaums.fapiao.demo.util.SignUtil;
import com.chinaums.fapiao.demo.util.UUIDGenerator;

/**
 * 开具发票
 */
public class TestIssue {

    public static String getRequestJson() {
        JSONObject req = new JSONObject();

        req.put("msgType", "issue");
        req.put("msgId", UUIDGenerator.getUUID());
        req.put("msgSrc", "PUBLIC_TEST");
        req.put("requestTimestamp", String.format("%1$tF %1$tT",Calendar.getInstance().getTimeInMillis()));
        req.put("srcReserve", "开具发票");

        req.put("invoiceMaterial", "ELECTRONIC");
        req.put("invoiceType", "PLAIN");

        req.put("merchantId", "898000000000001");
        req.put("terminalId", "00000001");
        req.put("merOrderDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        req.put("merOrderId", "455855485835858585345");

        req.put("amount", 10000);

        JSONArray goods = new JSONArray();
        JSONObject good1 = new JSONObject();
        goods.add(good1);
        good1.put("index", 1);
        good1.put("attribute", "0");
        good1.put("discountIndex", null);
        good1.put("name", "花生");
        good1.put("sn", "1010103010000000000");
        good1.put("taxRate", 6);
        good1.put("priceIncludingTax", 45);
        good1.put("quantity", 1);
        good1.put("unit", "斤");
        good1.put("model", "");

        JSONObject good2 = new JSONObject();
        goods.add(good2);
        good2.put("index", 2);
        good2.put("attribute", "0");
        good2.put("discountIndex", null);
        good2.put("name", "芝麻");
        good2.put("sn", "1010103030000000000");
        good2.put("taxRate", 6);
        good2.put("priceIncludingTax", 55);
        good2.put("quantity", 2);
        good2.put("unit", "斤");
        good2.put("model", "");

        req.put("goodsDetail", goods.toString());

        req.put("buyerName", "测试企业");
        req.put("buyerTaxCode", "445477454545454");
        req.put("buyerAddress", "上海");
        req.put("buyerTelephone", "8585456");
        req.put("buyerBank", "中国银行");
        req.put("buyerAccount", "6222222222222222");

        req.put("notifyMobileNo", "");
        req.put("notifyEMail", "1013168814@qq.com");

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
