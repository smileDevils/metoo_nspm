package com.cloud.tv.core.service.zabbix.impl;

import com.cloud.tv.core.manager.zabbix.utils.ZabbixApiUtil;
import com.cloud.tv.core.service.zabbix.ITemplateService;
import com.cloud.tv.dto.zabbix.TemplateDTO;
import io.github.hengyunabc.zabbix.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements ITemplateService {


    @Autowired
    private ZabbixApiUtil zabbixApiUtil;

    @Override
    public Object getTemplate(TemplateDTO dto) {
        Request request = this.zabbixApiUtil.parseParam(dto, "template.get");
        return zabbixApiUtil.call(request);
    }
}
