package com.cloud.tv.core.http;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class HttpBase {

    private String url;
    private String token;
    private MultiValueMap<String, Object> map;

    public HttpBase(String url, String token, MultiValueMap<String, Object> map) {
        this.url = url;
        this.token = token;
        this.map = map;
    }

    private static RestTemplate restTemplate;

    public ClientHttpRequestFactory init(){
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(65000) // 服务器返回数据(response)的时间，超时抛出read timeout
                .setConnectTimeout(65000) // 连接上服务器(握手成功)的时间，超时抛出connect timeout
                .setConnectionRequestTimeout(1000)// 从连接池中获取连接的超时时间，超时抛出ConnectionPoolTimeoutException
                .build();
        SSLContext sslContext = null;
        try {
            sslContext = SSLContextBuilder.create().setProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
            HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setSSLContext(sslContext).setSSLHostnameVerifier((x, y) -> true).build();
            ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            return requestFactory;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object get(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);// 设置密钥
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (!map.isEmpty()) {
           for(Map.Entry e : map.entrySet()){
               builder.build(e.getKey(), e.getValue());
           }
        }
        String realUrl = builder.build().toString();
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(null, headers);
        restTemplate = new RestTemplate(init());
        ResponseEntity<String> result = restTemplate.exchange(realUrl, HttpMethod.GET, httpEntity, String.class);
        return result.getBody();
    }


}
