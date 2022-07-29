package com.cloud.tv.core.service.zabbix;

import com.cloud.tv.dto.zabbix.HistoryDTO;

public interface ZabbixHistoryService {

    Object getHistory(HistoryDTO dto);
}
