package com.cloud.tv.core.service;

import com.cloud.tv.entity.SysConfig;

public interface ISysConfigService {

    SysConfig findObjById(Long id);

    SysConfig findSysConfigList();

    int modify(SysConfig instance);

    boolean update(SysConfig instance);
}
