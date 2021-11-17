package com.youle.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;
import com.youle.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName： SpringSecurityUserService
 * @Description: 用户登录认证服务器
 * @Author: 梅哲豪
 * @Date: 2021/11/4 15:09
 * @Version: 1.0
 */
@Component("springSecurityUserService")
public class SpringSecurityUserService implements UserDetailsService {
    //    使用dubbo通过网络远程调用服务提供的获取数据库功能的方法
    @Reference
    private UserService userService;

    //    根据用户名查询用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       /* User userInfo = userService.findByUserName(username);
        if (userInfo == null) {
//            当前用户不存在
            return null;
        }
//        用户存在
        List<GrantedAuthority> list = new ArrayList<>();
//        动态为当前用户授权
        Set<Role> userInfoRoles = userInfo.getRoles();//获取用户角色
        for (Role userInfoRole : userInfoRoles) {
            list.add(new SimpleGrantedAuthority(userInfoRole.getKeyword()));// 获取角色名称
            for (Permission permission : userInfoRole.getPermissions()) {
//                遍历资源权限集合，为角色赋予角色权限
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));//添加资源权限
            }
        }
        return new org.springframework.security.core.userdetails.User(username, userInfo.getPassword(), list);*/
        //远程调用用户服务，根据用户名查询用户信息
        com.youle.pojo.User user = userService.findByUserName(username);
        if(user == null){
           //用户名不存在
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>(); Set<Role> roles = user.getRoles();
        for(Role role : roles){
                //授予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for(Permission permission : permissions){
                //授权
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return userDetails;
    }
}
