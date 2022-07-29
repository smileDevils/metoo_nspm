package com.cloud.tv.core.service.zabbix;

import com.cloud.tv.dto.zabbix.ItemDTO;

public interface ZabbixItemService {

    Object getItem(ItemDTO dto);
}
