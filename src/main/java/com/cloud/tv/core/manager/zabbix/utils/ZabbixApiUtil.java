package com.cloud.tv.core.manager.zabbix.utils;

import com.alibaba.fastjson.JSONObject;
import com.cloud.tv.dto.zabbix.ParamsDTO;
import io.github.hengyunabc.zabbix.api.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class ZabbixApiUtil {

    public static final String URL = "http://192.168.5.111/api_jsonrpc.php";
    public static final String USER = "Admin";
    public static final String PASSWORD = "zabbix";
    public static ZabbixApi ZABBIX_API = null;

    @PostConstruct
    public void init() {
        ZabbixApi zabbixApi = new DefaultZabbixApi(ZabbixApiUtil.URL);
        zabbixApi.init();
        boolean login = zabbixApi.login(ZabbixApiUtil.USER, ZabbixApiUtil.PASSWORD);
        ZABBIX_API = zabbixApi;
    }

    public static JSONObject call(Request request){
        JSONObject resJson = ZABBIX_API.call(request);
        return resJson;
    }

    public static JSONObject call(DeleteRequest request){
        JSONObject resJson = ZABBIX_API.call(request);
        return resJson;
    }


    public static Request parseParam(ParamsDTO dto, String method){
        RequestBuilder requestBuilder = RequestBuilder.newBuilder().method(method);
        if(dto != null){
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(dto));
            for(Map.Entry<String, Object> entry : json.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();
                if(value != null){
                    requestBuilder.paramEntry(key, value);
                }
            }
        }
        return requestBuilder.build();
    }

    public static DeleteRequest parseArrayParam(ParamsDTO dto, String method){
        DeleteRequestBuild deleteRequestBuild = DeleteRequestBuild.newBuilder().method(method);
        if(dto != null){
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(dto));
            for(Map.Entry<String, Object> entry : json.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();
                if(value != null){
                    deleteRequestBuild.paramEntry(Integer.parseInt(value.toString()));
                }
            }
        }
        return deleteRequestBuild.build();
    }

//    public static ZabbixApi zabbixApi(){
//        ZabbixApi zabbixApi = new DefaultZabbixApi(ZabbixApiUtil.URL);
//        zabbixApi.init();
//        boolean login = zabbixApi.login(ZabbixApiUtil.USER, ZabbixApiUtil.PASSWORD);
//        ZABBIX_API = zabbixApi;
//        return zabbixApi;
//    }
}
