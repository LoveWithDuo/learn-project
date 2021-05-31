package com.dw.demo.shiro;
/**
 * @Author: zhanzhihong
 * @Date: 2021/2/25 17:42
 * @version v1.0
 */

import com.dw.demo.entity.Permission;
import com.dw.demo.entity.User;
import com.dw.demo.entity.vo.RoleVo;
import com.dw.demo.service.UserService;
import com.dw.demo.util.common.MD5Utils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义realm
 * @author shengwu ni
 */
public class MyShiroRealm extends AuthorizingRealm {

    public static final String AUTHORIZATION = "Authorization";

    private static final transient Logger log = LoggerFactory.getLogger(MyShiroRealm.class);

    @Resource
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //如果身份认证的时候没有传入User对象，这里只能取到userName
        //也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<RoleVo> roles = userService.getRoles(user.getId());
        if(roles!=null && roles.size()>0){
            for (RoleVo roleVo : roles){
                //添加角色
                authorizationInfo.addRole(roleVo.getRolename());
                List<Permission> permissionList = roleVo.getPermissionList();
                if (permissionList!=null && permissionList.size()>0){
                    for (Permission permission : permissionList){
                        //添加权限
                        authorizationInfo.addStringPermission(permission.getUrl());
                    }
                }
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        // 根据用户名从数据库中查询该用户
        User user = userService.getByUsername(username);
        if (user != null) {
            // 把当前用户存到 Session 中
//            SecurityUtils.getSubject().getSession().setAttribute("user", user);
            if (!MD5Utils.MD5Encode(password, MD5Utils.UTF_8).equals(user.getPassword())){
//            if (!password.equals(user.getPassword())) {
                throw new IncorrectCredentialsException("密码错误");
            }
            // 传入用户名和密码进行身份认证，并返回认证信息
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, password, getName());
            return authcInfo;
        } else {
            return null;
        }
    }

}
