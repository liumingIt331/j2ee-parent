package com.chinaums.fapiao.demo.invoiceapi;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.chinaums.fapiao.demo.util.HttpClient;
import com.chinaums.fapiao.demo.util.SignUtil;
import com.chinaums.fapiao.demo.util.UUIDGenerator;

/**
 * 下载版式文件
 */
public class TestPickUp {

	public static String getRequestJson() {
		JSONObject req = new JSONObject();

		req.put("msgType", "pickup");
		req.put("msgId", UUIDGenerator.getUUID());
		req.put("msgSrc", "PUBLIC_TEST");
		req.put("requestTimestamp", String.format("%1$tF %1$tT",Calendar.getInstance().getTimeInMillis()));
		req.put("srcReserve", "下载版式文件");

		req.put("merchantId", "898222222222222");
		req.put("terminalId", "00000001");
		req.put("merOrderDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
		req.put("merOrderId", "I161587641860930488");
		req.put("needImg", true);

		String key = "c8a9236183cb47ab82bb2fab2df0a13a";
		String sign = SignUtil.signWithSha(req, key, "utf-8");
		req.put("sign", sign);

		return req.toString();
	}

	public static void main(String[] args) {

		String url = "https://fapiao-test.chinaums.com/fapiao-api-test/";

		String req = getRequestJson();

		System.out.println("req: " + req);

		HttpClient httpClient = new HttpClient();

		String resp = httpClient.doPost(url, req);

		System.out.println("\r\nresp: " + resp);
	}

}
