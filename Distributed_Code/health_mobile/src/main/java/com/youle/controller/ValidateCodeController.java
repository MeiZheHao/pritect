package com.youle.controller;

import com.youle.constant.MessageConstant;
import com.youle.constant.RedisMessageConstant;
import com.youle.entity.Result;
import com.youle.utils.QianYiMessage;
import com.youle.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName： ValidateCodeController
 * @Description: 短信验证码的控制层
 * @Author: 梅哲豪
 * @Date: 2021/11/3 13:09
 * @Version: 1.0
 */
@RequestMapping("/validateCode")
@RestController
public class ValidateCodeController {
//   用户预约时验证码发送方法
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/sendOrder.do")
    public Result validateCode(String telephone) {
//        借助工具类随机生成6位短信验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        try {
            QianYiMessage.sendMessage(telephone,validateCode);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
//        将用户手机号对应的验证码存入redis，保存时间为5分钟
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 300, validateCode.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
// 用户登录时验证码发送方法
    @RequestMapping("/send4Login.do")
    public Result send4Login(String telephone) {
        //        借助工具类随机生成6位短信验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        try {
            QianYiMessage.sendMessage(telephone,validateCode);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
//        将用户手机号对应的验证码存入redis，保存时间为5分钟
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 300, validateCode.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
