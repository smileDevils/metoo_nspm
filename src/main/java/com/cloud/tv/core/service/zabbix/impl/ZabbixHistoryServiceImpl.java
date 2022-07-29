package com.cloud.tv.core.service.zabbix.impl;

import com.cloud.tv.core.manager.zabbix.utils.ZabbixApiUtil;
import com.cloud.tv.core.service.zabbix.ZabbixHistoryService;
import com.cloud.tv.core.service.zabbix.ZabbixService;
import com.cloud.tv.dto.zabbix.HistoryDTO;
import io.github.hengyunabc.zabbix.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZabbixHistoryServiceImpl implements ZabbixHistoryService {

    @Autowired
    private ZabbixApiUtil zabbixApiUtil;

    @Override
    public Object getHistory(HistoryDTO dto) {
        Request request = this.zabbixApiUtil.parseParam(dto, "history.get");
        return zabbixApiUtil.call(request);
    }
}
