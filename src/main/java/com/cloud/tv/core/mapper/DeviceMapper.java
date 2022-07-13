package com.cloud.tv.core.mapper;

import com.cloud.tv.entity.Device;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceMapper {

    List<Device> query();
}
