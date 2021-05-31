package com.dw.demo.service.impl;

import com.dw.demo.entity.*;
import com.dw.demo.entity.vo.RoleVo;
import com.dw.demo.service.*;
import com.dw.demo.util.common.BaseSerVice;
import com.dw.demo.util.common.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version v1.0
 * @Author: zhanzhihong
 * @Date: 2021/2/25 17:53
 */
@Service
public class UserServiceImpl extends BaseSerVice<User> implements UserService {
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RolePermissionService rolePermissionService;

    @Override
    public List<RoleVo> getRoles(Long userId) {
        List<RoleVo> roleVoList = new ArrayList<>();
        //根据名称查询用户
        User user = selectByPrimaryKey(userId);
        //查询用户角色
        List<UserRole> userRoleList = userRoleService.selectByExample(Example.builder(UserRole.class).andWhere(Sqls.custom().andEqualTo("userId", user.getId())).build());
        if (userRoleList!=null && userRoleList.size()>0){
            List<Long> userRoleIds = userRoleList.stream().map(UserRole::getId).collect(Collectors.toList());
            //查询角色
            List<Role> roleList = roleService.selectByExample(Example.builder(Role.class).andWhere(Sqls.custom().andIn("id", userRoleIds)).build());

            if (roleList!=null && roleList.size()>0){
                for (Role role : roleList){
                    RoleVo roleVo = CopyUtil.copyNew(role, RoleVo.class);
                    //查询角色权限
                    List<RolePermission> rolePermissionList = rolePermissionService.selectByExample(Example.builder(RolePermission.class).andWhere(Sqls.custom().andEqualTo("roleId", role.getId())).build());
                    if (rolePermissionList != null && rolePermissionList.size()>0){
                        //获取权限id
                        List<Long> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
                        List<Permission> permissions = permissionService.selectByExample(Example.builder(Permission.class).andWhere(Sqls.custom().andIn("id", permissionIds)).build());
                        roleVo.setPermissionList(permissions);
                    }
                    roleVoList.add(roleVo);
                }
            }
        }
        //去重
        return roleVoList;
    }

    @Override
    public User getByUsername(String username) {
        //根据名称查询用户
        User user = this.selectOne(Example.builder(User.class).andWhere(Sqls.custom().andEqualTo("username", username)).build());
        return user;
    }
}