package com.cloud.tv.core.service.zabbix.impl;

import com.cloud.tv.core.manager.zabbix.utils.ZabbixApiUtil;
import com.cloud.tv.core.service.zabbix.ProblemService;
import com.cloud.tv.dto.zabbix.ProblemDTO;
import io.github.hengyunabc.zabbix.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ZabbixApiUtil zabbixApiUtil;


    @Override
    public Object get(ProblemDTO dto) {
        Request request = this.zabbixApiUtil.parseParam(dto, "problem.get");
        return zabbixApiUtil.call(request);
    }
}
