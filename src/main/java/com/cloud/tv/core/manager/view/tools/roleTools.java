package com.cloud.tv.core.manager.view.tools;

import com.cloud.tv.core.service.IRoleService;
import com.cloud.tv.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class roleTools {


    private IRoleService roleService;

    public List<Role> getAllRole(String type){
        List<Role> roles = this.roleService.findRoleByType(type);

        return roles;
    }
}
