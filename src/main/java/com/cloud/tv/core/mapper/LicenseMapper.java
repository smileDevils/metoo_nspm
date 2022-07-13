package com.cloud.tv.core.mapper;

import com.cloud.tv.entity.License;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LicenseMapper {

    List<License> query();

    int update(License instance);
}
