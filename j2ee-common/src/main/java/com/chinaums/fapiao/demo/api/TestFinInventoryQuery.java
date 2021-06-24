package com.chinaums.fapiao.demo.api;

import com.alibaba.fastjson.JSONObject;
import com.chinaums.fapiao.demo.util.HttpClient;
import com.chinaums.fapiao.demo.util.SignUtil;
import com.chinaums.fapiao.demo.util.UUIDGenerator;

import java.util.Calendar;

/**
 * 发票状态查询
 */
public class TestFinInventoryQuery {

    public static String getRequestJson() {
        JSONObject req = new JSONObject();

        req.put("msgType", "finance.inventory.query");
        req.put("msgId", UUIDGenerator.getUUID());
        req.put("msgSrc", Constant.MSG_SRC);
        req.put("requestTimestamp", String.format("%1$tF %1$tT",Calendar.getInstance().getTimeInMillis()));
        req.put("srcReserve", "财政票据库存查询");

        req.put("merchantId", "898000099000001");
        req.put("terminalId", "00000009");
        req.put("beginDate", "20210301");
        req.put("endDate", "20210331");

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
