package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.youle.constant.MessageConstant;
import com.youle.dao.MemberDao;
import com.youle.dao.OrderDao;
import com.youle.dao.OrderSettingDao;
import com.youle.entity.Result;
import com.youle.pojo.Member;
import com.youle.pojo.Order;
import com.youle.pojo.OrderSetting;
import com.youle.service.OrderService;
import com.youle.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName： OrderServiceImpl
 * @Description: 用户预约的业务层
 * @Author: 梅哲豪
 * @Date: 2021/11/3 15:24
 * @Version: 1.0
 */
@Service(interfaceClass = OrderService.class)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Override
    public Result order(Map map) throws Exception {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(DateUtils.parseString2Date(orderDate));
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int orderNumber = orderSetting.getNumber();//可预约人数
        int reservations = orderSetting.getReservations();//已经预约人数
        if (reservations > orderNumber) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再 次预约
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if (member != null) {
//            判断会员是否重复预约
            Integer memberId = member.getId();//会员id
            Date order_Date = DateUtils.parseString2Date(orderDate);//预约日期
            String setmealId = (String) map.get("setmealId");//套餐ID
            Order order = new Order(memberId, order_Date, Integer.parseInt(setmealId));
            List<Order> orderList = orderDao.findByCondition(order);
            if (orderList != null && orderList.size() > 0) {
                //说明用户已经预约过了不能重复预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        } else {
            //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setName((String)map.get("name"));
            member.setIdCard((String)map.get("idCard"));
            member.setSex((String)map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }
        //5、预约成功，更新当日的已预约人数
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(DateUtils.parseString2Date(orderDate));
        order.setOrderType((String)map.get("orderType"));//预约类型  微信 电话
        order.setOrderStatus(Order.ORDERSTATUS_NO);//预约状态  未到珍  已到珍
        order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        orderDao.add(order);
        //预约信息在保存之后 应该去减少预约时间的可预约人数
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    @Override
    public Map findById(Integer id) {
        Map map = orderDao.findById4Detail(id);//
        if(map != null){
            //处理日期格式
            Date orderDate = (Date)map.get("orderDate");
            map.put("orderDate",orderDate);
        }
        return map;
    }
}
