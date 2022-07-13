package com.cloud.tv.core.mapper;

import com.cloud.tv.entity.Group;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupMapper {

    List<Group> query(Map map);

    Group queryObjById(Long id);

    Group getObjByLevel(String level);

    List<Group> queryChild(Long id);

    boolean save(Group instance);

    boolean update(Group instance);

    boolean del(Long id);

    Group getMaxBranch(String level);
}
