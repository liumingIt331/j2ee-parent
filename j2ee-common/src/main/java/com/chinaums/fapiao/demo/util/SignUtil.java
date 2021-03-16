package com.chinaums.fapiao.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
public class SignUtil {

    static public String buildSignString(String jsonStr) {
        // 加上特征值Feature.OrderedField，维持原参数的顺序，否则在goodsDetail的值为数组时，数组对象的参数顺序会被打乱，
        // 造成验签和签名字符串不一致，验签失败，值为字符串时不会有这个问题
        JSONObject json = JSONObject.parseObject(jsonStr, Feature.OrderedField);
        return buildSignString(json);
    }

    static public String buildSignString(JSONObject json) {
        Map<String, String> map = new HashMap<String, String>();
        for (Object key : json.keySet()) {
            String value = json.getString((String) key);
            map.put((String) key, value);
        }
        return buildSignString(map);
    }

    static public String buildSignString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.size());

        for (String key : params.keySet()) {
            if ("sign".equals(key) || "sign_type".equals(key))
                continue;
            if (StringUtils.isEmpty(params.get(key)))
                continue;
            keys.add(key);
        }

        Collections.sort(keys);

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                buf.append(key + "=" + value);
            } else {
                buf.append(key + "=" + value + "&");
            }
        }

        return buf.toString();
    }

    /**
     * 签名方法1，入参Json字符串
     *
     * @param jsonStr
     * @param shaKey
     * @param charset
     * @return
     */
    static public String signWithSha(String jsonStr, String shaKey, String charset) {
        String prestr = buildSignString(jsonStr); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        return sign(prestr, shaKey, charset);
    }

    /**
     * 签名方法2，入参Json对象
     *
     * @param jsonStr
     * @param shaKey
     * @param charset
     * @return
     */
    static public String signWithSha(JSONObject json, String shaKey, String charset) {
        String prestr = buildSignString(json); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        return sign(prestr, shaKey, charset);
    }

    /**
     * 签名方法3，入参Map
     *
     * @param jsonStr
     * @param shaKey
     * @param charset
     * @return
     */
    static public String signWithSha(Map<String, String> params, String shaKey, String charset) {
        String prestr = buildSignString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        return sign(prestr, shaKey, charset);
    }

    static private String sign(String originStr, String shaKey, String charset) {
        String text = originStr + shaKey;
        return DigestUtils.sha256Hex(getContentBytes(text, charset)).toUpperCase();
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
                    + charset);
        }
    }

    public static void main(String[] args) {
        String jsonStr = "{\"amount\":\"258\",\"invoiceMaterial\":\"ELECTRONIC\",\"invoiceType\":\"PLAIN\",\"merchantId\":\"898000000000001\",\"merOrderDate\":\"20171130\",\"merOrderId\":\"X1000511G1711308844\",\"msgId\":\"a57c3a09dba943aba072deef2855511d\",\"msgSrc\":\"SMART_ID\",\"msgType\":\"get.qrcode\",\"requestTimestamp\":\"2017-11-30 19:18:00\",\"terminalId\":\"00000001\"}";
        String key = "5e38e904b4784098955f38f469c49819";
        String charset = "UTF-8";

        String sign = signWithSha(jsonStr, key, charset);
        System.out.println(sign);
    }

}
