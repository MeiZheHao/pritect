package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.constant.RedisMessageConstant;
import com.youle.entity.Result;
import com.youle.pojo.Order;
import com.youle.service.OrderService;
import com.youle.utils.QianYiMessage;
import com.youle.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @ClassName： OrderController
 * @Description: 预约控制层
 * @Author: 梅哲豪
 * @Date: 2021/11/3 15:05
 * @Version: 1.0
 */
@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;
    @RequestMapping("/submit.do")
    public Result submit(@RequestBody Map map) throws Exception {

//        获取前端中用户填写的电话和验证码
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
//        取出redis中存储的验证码
        String validateCodeRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
//        判断两个验证码是否一致
        if (validateCodeRedis != null && validateCode != null && validateCodeRedis.equals(validateCode)) {
//            调用业务层
//            设置预约类型
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            Result result = orderService.order(map);
            if (result.isFlag()) {
//                预约成功给用户发送短信
                try {
                    QianYiMessage.sendMessage(telephone, ValidateCodeUtils.generateValidateCode(6));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;

        } else {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

    }

    @RequestMapping("/findById.do")
    public Result findById(Integer id) {
        try {
            Map map = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
