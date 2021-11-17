package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.youle.dao.OrderSettingDao;
import com.youle.pojo.OrderSetting;
import com.youle.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName： OrderSettingServiceImpl
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/11/1 17:34
 * @Version: 1.0
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettingList) {
//        批量导入预约数据
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
//                查询当天是否已经存在预约信息
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (count > 0) {
//                    如果当天已经存在预约信息则修改
                    orderSettingDao.edit(orderSetting);
                } else {
//                    不存在预约信息则添加
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> orderSettingByMonth(String date) {//日期格式 yyyy-MM
        String begin = date + "-1";
        String end = date + "-31";
        Map<String, String> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
//        根据日期范围查询预约信息
        List<OrderSetting> orderSettings = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if (orderSettings != null && orderSettings.size() > 0) {
            for (OrderSetting orderSetting : orderSettings) {
                Map<String, Object> orderMap = new HashMap<>();
                orderMap.put("date", orderSetting.getOrderDate().getDate());
                orderMap.put("number", orderSetting.getNumber());
                orderMap.put("reservations", orderSetting.getReservations());
                result.add(orderMap);
            }
        }
        return result;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
//        根据日期查询是否设置过预约人数
        long countByOrderDate = orderSettingDao.findCountByOrderDate(orderDate);
        if (countByOrderDate > 0) {
//            代表已经设置过这是执行修改操作
            orderSettingDao.edit(orderSetting);
        } else {
//            代表没有设置过执行插入操作
            orderSettingDao.add(orderSetting);
        }
    }
}
