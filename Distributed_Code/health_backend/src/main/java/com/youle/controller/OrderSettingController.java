package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.entity.Result;
import com.youle.pojo.OrderSetting;
import com.youle.service.OrderSettingService;
import com.youle.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName： OrderSettingController
 * @Description: 预约管理控制层
 * @Author: 梅哲豪
 * @Date: 2021/11/1 17:31
 * @Version: 1.0
 */
@RequestMapping("/ordersetting")
@RestController
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload.do")
    public Result add(@RequestParam(required = true,name = "excelFile")MultipartFile excelFile) {
        try {
            List<String[]> excel = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettingList = new ArrayList<>();
            for (String[] strings : excel) {
                String orderDate = strings[0];
                String orderNum = strings[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate), Integer.parseInt(orderNum));
                orderSettingList.add(orderSetting);
            }
            //                调用业务层将预约数据存入数据库
            orderSettingService.add(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/orderSettingByMonth.do")
    public Result orderSettingByMonth(String date) {
        try {
            List<Map> list = orderSettingService.orderSettingByMonth(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editNumberByDate.do")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
