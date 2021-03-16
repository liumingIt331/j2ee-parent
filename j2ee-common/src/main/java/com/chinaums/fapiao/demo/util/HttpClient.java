package com.chinaums.fapiao.demo.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

//实现Post消息推送，故采用HttpClient组件进行实现
public class HttpClient {

    private CloseableHttpClient httpClient;

    public HttpClient() {
        initialize();
    }

    private void initialize() {
        try {
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000)
                    .setConnectTimeout(10000).setSocketTimeout(5000).build();
            HttpClientBuilder builder = HttpClients.custom().setMaxConnTotal(500).setMaxConnPerRoute(500)
                    .setDefaultRequestConfig(requestConfig);
            httpClient = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void destroy() {
        try {
            // 关闭连接,释放资源
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String doPost(String url, String request) {
        HttpPost httpPost = null;
        CloseableHttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        // 构造消息头
        try {
            httpPost = new HttpPost(url);
            // 构建消息实体
            if (StringUtils.isNotBlank(request)) {
                StringEntity stringEntity = new StringEntity(request, "UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);// 发送Json格式的数据请求
            }

            httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();
            // 检验返回码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                return EntityUtils.toString(httpEntity, "UTF-8");
            } else {
                System.out.println("HTTP-POST状态码异常 {}" + statusCode);
            }
        } catch (Exception e) {
            System.out.println("HTTP-POST出错" + e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (httpResponse != null) {
                    httpResponse.close();
                }
                destroy();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
