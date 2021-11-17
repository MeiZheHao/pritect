package com.youle.service;

import com.youle.entity.PageResult;
import com.youle.entity.QueryPageBean;
import com.youle.pojo.Setmeal;

import java.util.List;

public interface SetMealService {
    void add(Setmeal setmeal, Integer[] checkGroupIds);

    PageResult pageQuery(QueryPageBean queryPageBean);

    List<Setmeal> getAllSetMeal();

    Setmeal findById(Integer id);
}
