package com.chinaums.fapiao.demo.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinaums.fapiao.demo.util.HttpClient;
import com.chinaums.fapiao.demo.util.SignUtil;
import com.chinaums.fapiao.demo.util.UUIDGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 开具发票
 */
public class TestFinIssue {

    public static String getRequestJson() {
        JSONObject req = new JSONObject();

        req.put("msgType", "finance.issue");
        req.put("msgId", UUIDGenerator.getUUID());
        req.put("msgSrc", "FINANCE_HAINAN");
        req.put("requestTimestamp", String.format("%1$tF %1$tT", Calendar.getInstance().getTimeInMillis()));
        req.put("srcReserve", "财政票据开具");

        req.put("bizType", "HOSPITALIZED");
        req.put("bizNo", UUIDGenerator.getUUID());
        req.put("bizDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        req.put("bizTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        req.put("invoiceTypeCode", "0401");

        req.put("merchantId", "898004010900041");
        req.put("terminalId", "10900041");

        req.put("payerName", "莫安锐1");
        req.put("payerCode", "460023197605010837");
        req.put("payerType", "1");
        req.put("payerBank", "中国银行");
        req.put("payerAccount", "622111233333333313");
        req.put("payerGender", "男");
        req.put("payerAge", "46");

        req.put("patientNo", "000610511200");
        req.put("patientDate", "20201127");
        req.put("caseNo", "00061051120022");
        req.put("hospitalNo", "243811");
        req.put("inHospitalDate", "20201127");
        req.put("outHospitalDate", "20210201");
        req.put("medicalOrgType", "");
        req.put("medicalInsuranceTyp", "");
        req.put("medicalInsuranceNo", "");

        req.put("foundPayAmount", "");
        req.put("otherPayAmount", "");
        req.put("accountPayAmount", "");
        req.put("ownPayAmount", "34");
        req.put("selfPaymentAmount", "");
        req.put("selfCostAmount", "");
        req.put("prepayAmount", "");
        req.put("rechargeAmount", "34");
        req.put("refundAmount", "0");
        req.put("totalAmount", "34");

        req.put("remark", "备注");
        req.put("payee", "管理员");
        req.put("checker", "管理员");
        req.put("drawer", "管理员");
        req.put("notifyMobileNo", "");
        req.put("notifyEMail", "");
        req.put("notifyUrl", "");

        JSONArray chargeItemDetail = new JSONArray();
        JSONObject chargeItem1 = new JSONObject();
        chargeItemDetail.add(chargeItem1);
        chargeItem1.put("name", "诊察费");
        chargeItem1.put("code", "YL11");
        chargeItem1.put("std", "17");
        chargeItem1.put("amount", "17");
        chargeItem1.put("unit", "");
        chargeItem1.put("quantity", "1");
        chargeItem1.put("selfAmount", "");
        chargeItem1.put("remark", "");

        JSONObject chargeItem2 = new JSONObject();
        chargeItemDetail.add(chargeItem2);
        chargeItem2.put("name", "护理费");
        chargeItem2.put("code", "YL13");
        chargeItem2.put("std", "17");
        chargeItem2.put("amount", "17");
        chargeItem2.put("unit", "");
        chargeItem2.put("quantity", "1");
        chargeItem2.put("selfAmount", "");
        chargeItem2.put("remark", "");

        JSONArray listItemDetail = new JSONArray();
        JSONObject listItem1 = new JSONObject();
        listItemDetail.add(listItem1);
        listItem1.put("itemTypeCode", "046");
        listItem1.put("itemTypeName", "诊查费");
        listItem1.put("name", "诊察费");
        listItem1.put("code", "YL11");
        listItem1.put("std", "17");
        listItem1.put("amount", "17");
        listItem1.put("unit", "");
        listItem1.put("quantity", "1");
        listItem1.put("selfAmount", "");
        listItem1.put("remark", "");

        JSONObject listItem2 = new JSONObject();
        listItemDetail.add(listItem2);
        listItem2.put("itemTypeCode", "028");
        listItem2.put("itemTypeName", "护理费");
        listItem2.put("name", "护理费");
        listItem2.put("code", "YL13");
        listItem2.put("std", "17");
        listItem2.put("amount", "17");
        listItem2.put("unit", "");
        listItem2.put("quantity", "1");
        listItem2.put("selfAmount", "");
        listItem2.put("remark", "");

        req.put("chargeItemDetail", chargeItemDetail.toString());
        req.put("listItemDetail", listItemDetail.toString());

        String key = "3bba5ba89a0b4041bc77e0b2dfb8b042";
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
