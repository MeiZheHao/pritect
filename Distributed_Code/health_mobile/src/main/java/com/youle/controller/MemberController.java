package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.youle.constant.MessageConstant;
import com.youle.constant.RedisMessageConstant;
import com.youle.entity.Result;
import com.youle.pojo.Member;
import com.youle.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName： MemberController
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/11/3 17:22
 * @Version: 1.0
 */
@RequestMapping("/member")
@RestController
public class MemberController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/login.do")
    public Result login(HttpServletResponse response, @RequestBody Map map) {

//        电话号码
        String telephone = (String) map.get("telephone");
//        短信验证码
        String validateCode = (String) map.get("validateCode");
        String validateCodeRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (validateCodeRedis != null && validateCode != null && validateCodeRedis.equals(validateCode)) {
//              判断当前用户是否是会员，如果不是会员则自动注册
            Member member = memberService.findByTelephone(telephone);
            if (member == null) {
//                该用户不是会员自动注册
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
//            向客户端发送cookie
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookie);
//            存入redis保留30分钟
            String memberJson = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone, 60 * 30, memberJson);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        } else {
//            验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }
}
