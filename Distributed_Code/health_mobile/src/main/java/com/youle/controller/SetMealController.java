package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.entity.Result;
import com.youle.pojo.Setmeal;
import com.youle.service.SetMealService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName： SetMealController
 * @Description: 移动端套餐管理控制层
 * @Author: 梅哲豪
 * @Date: 2021/11/2 15:49
 * @Version: 1.0
 */
@RequestMapping("/setMeal")
@RestController
public class SetMealController {
    @Reference
    private SetMealService setMealService;
    @RequestMapping("/getAllSetMeal.do")
    public Result getAllSetMeal() {
        try {
            List<Setmeal> list = setMealService.getAllSetMeal();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @RequestMapping("/findById.do")
    public Result findById(int id) {
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
