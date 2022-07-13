package com.cloud.tv.core.mapper;

import com.cloud.tv.entity.RoleGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleGroupMapper {

    RoleGroup selectByPrimaryKey(Long id);

    List<RoleGroup> selectByPrimaryType(String type);

    int insert(RoleGroup instance);

    int update(RoleGroup instance);

    List<RoleGroup> query();

    List<RoleGroup> roleUnitGroup();

    RoleGroup change(Long id);

    int selectObjByNameCount(String name);

    int delete(Long id);
}
