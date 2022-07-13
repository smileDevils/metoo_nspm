package com.cloud.tv.core.service.impl;

import com.cloud.tv.core.mapper.DeviceMapper;
import com.cloud.tv.core.service.IDeviceService;
import com.cloud.tv.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public List<Device> query() {
        return this.deviceMapper.query();
    }
}
