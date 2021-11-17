package com.youle.service;

import com.youle.entity.PageResult;
import com.youle.entity.QueryPageBean;
import com.youle.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    //    新增检查组
    void add(CheckGroup checkGroup, Integer[] checkItemId);


    PageResult pageQuery(QueryPageBean queryPageBean);

    CheckGroup findById(Integer checkGroupId);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    void edit(Integer[] checkItemsIds, CheckGroup checkGroup);

    void deleteById(Integer checkGroupId);

    List<CheckGroup> findAll();
}
