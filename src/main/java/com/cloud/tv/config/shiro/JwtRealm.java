package com.cloud.tv.config.shiro;

import com.cloud.tv.core.jwt.util.JwtToken;
import com.cloud.tv.core.service.IRegisterService;
import com.cloud.tv.core.service.IRoleService;
import com.cloud.tv.core.shiro.tools.ApplicationContextUtils;
import com.cloud.tv.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * JwtRealm 只负责校验 JwtToken
 */
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private IRoleService roleService;
    /**
     * 限定这个 Realm 只处理我们自定义的 JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {

        return token instanceof JwtToken;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        // 获取当前用户
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        // UserEntity currentUser = (UserEntity) principals.getPrimaryPrincipal();
//        // 查询数据库，获取用户的角色信息
//        List<Role> roles = this.roleService.findRoleByUserId(user.getId());
//        // 查询数据库，获取用户的权限信息
//        List<Res> permissions = resService.findResByRoleId(role.getId());
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setRoles(roles);
//        info.setStringPermissions(perms);
//        return info;
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authcToken;
        if (jwtToken.getPrincipal() == null) {
            throw new AccountException("JWT token参数异常！");
        }
        // 从 JwtToken 中获取当前用户
        String username = jwtToken.getPrincipal().toString();
        // 查询数据库获取用户信息
        IRegisterService registerService = (IRegisterService) ApplicationContextUtils.getBean("registerService");

        User user = registerService.findByUsername(username);


        // 用户不存在
        if (user == null) {
            throw new UnknownAccountException("用户不存在！");
        }

        // 用户被锁定
        if (user.isLocked()) {
            throw new LockedAccountException("该用户已被锁定,暂时无法登录！");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, username, getName());
        return info;
    }
}
