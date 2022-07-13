package com.cloud.tv.core.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PolicyStatisticalMapper {

    Double getObjByCode(String code);
}
