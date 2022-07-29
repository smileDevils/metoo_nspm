package com.cloud.tv.core.service.zabbix.impl;

import com.cloud.tv.core.manager.zabbix.utils.ZabbixApiUtil;
import com.cloud.tv.core.service.zabbix.ZabbixItemService;
import com.cloud.tv.dto.zabbix.ItemDTO;
import io.github.hengyunabc.zabbix.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZabbixItemServiceImpl implements ZabbixItemService {

    @Autowired
    private ZabbixApiUtil zabbixApiUtil;

    @Override
    public Object getItem(ItemDTO dto) {
        Request request = this.zabbixApiUtil.parseParam(dto, "item.get");
        return zabbixApiUtil.call(request);
    }
}
