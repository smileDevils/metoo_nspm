package com.cloud.tv.core.service;

import com.cloud.tv.dto.MonitorDto;
import com.cloud.tv.entity.Monitor;
import com.github.pagehelper.Page;

public interface IMonitorService {

    Monitor getObjById(Long id);

    Monitor getObjBySign(String sign);

    Page<Monitor> query(MonitorDto dto);

    boolean save(MonitorDto instance);

    boolean update(MonitorDto instance);

    int delete(Long id);
}
