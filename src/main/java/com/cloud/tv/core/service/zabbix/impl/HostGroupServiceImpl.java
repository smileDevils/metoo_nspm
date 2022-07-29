package com.cloud.tv.core.service.zabbix.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.tv.core.manager.zabbix.utils.ZabbixApiUtil;
import com.cloud.tv.core.service.zabbix.IHostGroupService;
import com.cloud.tv.dto.zabbix.HostGroupDTO;
import io.github.hengyunabc.zabbix.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class HostGroupServiceImpl implements IHostGroupService {

    @Autowired
    private ZabbixApiUtil zabbixApiUtil;

    private static final String NAME = "Templates/Network devices";

    @Override
    public Object get(HostGroupDTO dto) {
        Request request = this.zabbixApiUtil.parseParam(dto, "hostgroup.get");
        return zabbixApiUtil.call(request);
    }

    @Override
    public String getHostGroupId() {
        HostGroupDTO dto = new HostGroupDTO();
        dto.setName(Arrays.asList(NAME));
        Request request = this.zabbixApiUtil.parseParam(dto, "hostgroup.get");
        JSONObject json = zabbixApiUtil.call(request);
        if(json.getString("result") != null){
            JSONArray results = JSONArray.parseArray(json.getString("result"));
            if(results.size() > 0){
                for (Object result : results){
                    JSONObject element = JSONObject.parseObject(result.toString());
                    return element.getString("groupid");
                }
            }
        }
        return null;
    }
}
