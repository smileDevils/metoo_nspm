package com.cloud.tv.core.service;

import com.cloud.tv.entity.RoleRes;

import java.util.List;

public interface IRoleResService {

    /**
     * 批量添加角色资源
     * @param roleRes
     * @return
     */
    boolean batchAddRoleRes(List<RoleRes> roleRes);

    /**
     *根据角色ID删除 角色下所有模块
     * @param role_id 角色ID
     * @return
     */
    boolean deleteRoleResByRoleId(Long role_id);
}
