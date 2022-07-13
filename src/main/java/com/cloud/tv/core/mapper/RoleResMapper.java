package com.cloud.tv.core.mapper;

import com.cloud.tv.entity.RoleRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleResMapper {

    int insert(List<RoleRes> roleResList);

    int delete(Long id);
}
