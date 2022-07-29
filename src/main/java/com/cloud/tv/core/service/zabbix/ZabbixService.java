package com.cloud.tv.core.service.zabbix;

import java.util.List;

public interface ZabbixService {

    public Object getUsage(String ip, List itemName);

    /**
     * @param ip
     * @param itemName
     * @param limit
     * @param time_till
     * @param time_from
     * @return
     */
    Object getDeviceInfo(String ip, List itemName, Integer limit, Long time_till, Long time_from);

    Object refresh(String itemids, Integer limit);

    Object getInterfaceInfo(String ip);

    Object flow(String ip, String name);

    Object getInterfaceHistory(String ip, Integer limit, Long time_till, Long time_from);

}
