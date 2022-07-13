package com.cloud.tv.core.mapper;

import com.cloud.tv.entity.Invisible;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InvisibleMapper {

    // 查询所有未使用字符
    List<Invisible> query();

    int update(Invisible instance);
}
