package com.cloud.tv.core.service;

import com.cloud.tv.dto.TopoNodeDto;
import com.cloud.tv.entity.TopoNode;
import com.github.pagehelper.Page;

public interface INodeService {

    TopoNode getObjById(Long id);

    TopoNode getObjByHostAddress(String hostAddress);

    /**
     * 分页查询
     * @param nodeDto
     * @return
     */
    Page<TopoNode> query(TopoNodeDto nodeDto);

    int save(TopoNode instance);

    int update(TopoNode instance);

    int delete(Long id);
}
