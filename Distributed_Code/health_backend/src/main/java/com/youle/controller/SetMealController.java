package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.constant.RedisConstant;
import com.youle.entity.PageResult;
import com.youle.entity.QueryPageBean;
import com.youle.entity.Result;
import com.youle.pojo.Setmeal;
import com.youle.service.SetMealService;
import com.youle.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName： SetMealController
 * @Description: 套餐管理控制层
 * @Author: 梅哲豪
 * @Date: 2021/10/30 8:41
 * @Version: 1.0
 */
@RequestMapping("/setmeal")
@RestController
public class SetMealController {
    @Reference
    private SetMealService setMealService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload.do")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        String originalFilename = imgFile.getOriginalFilename();//获取原始图片名字
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String last = originalFilename.substring(lastIndexOf - 1);//.jpg
        System.out.println(last);
        String fileName = UUID.randomUUID().toString() + last;
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);

    }

    @RequestMapping("/add.do")
    public Result add(@RequestBody Setmeal setmeal, @RequestParam(required = true,name = "checkGroupIds") Integer[] checkGroupIds) {
        try {
            setMealService.add(setmeal, checkGroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setMealService.pageQuery(queryPageBean);
    }
}
