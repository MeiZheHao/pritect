package com.youle.dao;

import com.youle.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    void add(OrderSetting orderSetting);

    void edit(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    List<OrderSetting> getOrderSettingByMonth(Map map);

    //根据预约日期查询预约设置信息
    OrderSetting findByOrderDate(Date orderDate);

    //更新可预约人数
    void editNumberByOrderDate(OrderSetting orderSetting);

    //更新已预约人数
    void editReservationsByOrderDate(OrderSetting orderSetting);
}
