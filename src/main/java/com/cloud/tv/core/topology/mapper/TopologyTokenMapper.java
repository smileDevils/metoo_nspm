package com.cloud.tv.core.topology.mapper;

import com.cloud.tv.entity.TopologyToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TopologyTokenMapper {

    List<TopologyToken> query(Map map);
}
