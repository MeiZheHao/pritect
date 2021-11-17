package com.youle.dao;

import com.github.pagehelper.Page;
import com.youle.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void checkGroupAndCheckItem(Map map);

    Page<CheckGroup> findByCondition(String queryString);

    CheckGroup findById(Integer checkGroupId);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    void edit(CheckGroup checkGroup);

    void deleteAssoication(Integer id);

    void deleteCheckGroupById(Integer checkGroupId);

    List<CheckGroup> findAll();

    List<CheckGroup> findCheckGroupById(Integer setMealId);
}
