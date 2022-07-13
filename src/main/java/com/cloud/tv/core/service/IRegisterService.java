package com.cloud.tv.core.service;

import com.cloud.tv.entity.User;

public interface IRegisterService {

    /**
     * 注册用户
     *
     * @param user
     */
   int register(User user);


    User findByUsername(String username);

    /**
     * 根据用户名查询所有角色
     * @param username
     * @return
     */
    User findRolesByUserName(String username);
}
