package com.cloud.tv.core.service.zabbix;

import com.cloud.tv.dto.zabbix.HostDTO;

public interface ZabbixHostService {

    Object getHost(HostDTO dto);
}
