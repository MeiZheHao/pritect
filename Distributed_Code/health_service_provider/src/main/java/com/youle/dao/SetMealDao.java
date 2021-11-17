package com.youle.dao;

import com.github.pagehelper.Page;
import com.youle.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealDao {
    void add(Setmeal setmeal);

    void setSetMealAndGroup(Map map);

    Page<Setmeal> findByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
