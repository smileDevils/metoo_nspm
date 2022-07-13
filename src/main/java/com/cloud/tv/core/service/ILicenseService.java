package com.cloud.tv.core.service;

import com.cloud.tv.entity.License;

import java.util.List;

public interface ILicenseService {

    /**
     * 根据UUID检测是否为被允许设备
     */
    License detection();

    List<License> query();

    int update(License instance);

}
