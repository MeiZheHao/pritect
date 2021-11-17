package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.youle.dao.PermissionDao;
import com.youle.dao.RoleDao;
import com.youle.dao.UserDao;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;
import com.youle.pojo.User;
import com.youle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @ClassName： UserServiceImpl
 * @Description: 用户服务业务层
 * @Author: 梅哲豪
 * @Date: 2021/11/4 15:29
 * @Version: 1.0
 */
@Service(interfaceClass = UserService.class)
@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUserName(String username) {
        /*User userInfo = userDao.findByUserName(username);
        if (userInfo == null) {
            return null;
        }
        Integer userId = userInfo.getId();
//        根据用户id查询用户角色
        Set<Role> roles = roleDao.findByUserId(userId);
//        根据角色id查询资源权限
        for (Role role : roles) {
            Integer roleId = role.getId();
            Set<Permission> permissions = permissionDao.findByRoleId(roleId);
//            查询到之后为角色赋予对应的资源权限
            role.setPermissions(permissions);//让角色关联资源权限
        }
//        让用户关联角色
        userInfo.setRoles(roles);

        return userInfo;*/
        User user = userDao.findByUserName(username);
        if(user == null){
            return null;
        }
        Integer userId = user.getId();
        Set<Role> roles = roleDao.findByUserId(userId);
        if(roles != null && roles.size() > 0){
            for(Role role : roles){
                Integer roleId = role.getId();
                Set<Permission> permissions =permissionDao.findByRoleId(roleId);
                if(permissions != null && permissions.size() > 0){
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }
        return user;
    }
}
