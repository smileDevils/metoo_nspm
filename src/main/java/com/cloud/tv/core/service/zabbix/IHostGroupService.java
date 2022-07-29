package com.cloud.tv.core.service.zabbix;

import com.cloud.tv.dto.zabbix.HostGroupDTO;

public interface IHostGroupService {

    Object get(HostGroupDTO dto);

    String getHostGroupId();
}
