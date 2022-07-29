package com.cloud.tv.core.service.zabbix.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.tv.core.manager.zabbix.utils.ZabbixApiUtil;
import com.cloud.tv.core.service.zabbix.ZabbixHostService;
import com.cloud.tv.dto.zabbix.HostDTO;
import com.cloud.tv.dto.zabbix.ParamsDTO;
import io.github.hengyunabc.zabbix.api.Request;
import io.github.hengyunabc.zabbix.api.RequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ZabbixHostServiceImpl implements ZabbixHostService {

    @Autowired
    private ZabbixApiUtil zabbixApiUtil;

    @Override
    public Object getHost(HostDTO dto) {
        Request request = this.zabbixApiUtil.parseParam(dto, "host.get");
        return zabbixApiUtil.call(request);
    }

}
