package com.cloud.tv.core.service;

import com.cloud.tv.entity.Device;

import java.util.List;

public interface IDeviceService {

    /**
     * 查询所有设备类型
     * @return
     */
    List<Device> query();
}
