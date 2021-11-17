package com.youle.jobs;

import com.youle.constant.RedisConstant;
import com.youle.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        //计算在redis中两个set集合的差集  对云存储进行定时清理
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //进行清理
        if(set != null){
            for (String picName : set) {
                //从redis中删除差集部分的内容
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                int indexOf = picName.lastIndexOf("/");
                picName = picName.substring(indexOf + 1) ;
                //删除七牛云的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                System.out.println("定时任务开始执行，正在清理云储存的垃圾图片...");
            }
        }
    }
}
