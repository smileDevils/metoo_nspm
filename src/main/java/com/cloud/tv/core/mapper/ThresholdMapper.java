package com.cloud.tv.core.mapper;

import com.cloud.tv.entity.Threshold;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ThresholdMapper {

    Threshold query();

    int update(Threshold instance);
}
