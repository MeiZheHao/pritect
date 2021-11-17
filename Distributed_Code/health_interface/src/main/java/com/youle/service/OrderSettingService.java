package com.youle.service;

import com.youle.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @ClassName： OrderSettingService
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/11/1 17:33
 * @Version: 1.0
 */

public interface OrderSettingService {
    void add(List<OrderSetting> orderSettingList);

    List<Map> orderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
