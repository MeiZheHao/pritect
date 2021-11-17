package com.youle.controller;

import com.youle.constant.MessageConstant;
import com.youle.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName： UserController
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/11/4 20:22
 * @Version: 1.0
 */
@RequestMapping("/user")
@RestController
 public class UserController {
    //获取当前登录用户的用户名
    @RequestMapping("/getUsername")
    public Result getUsername()throws Exception{
        try{
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
    }catch (Exception e){
        return new Result(false, MessageConstant.GET_USERNAME_FAIL);
    }
    }
}
