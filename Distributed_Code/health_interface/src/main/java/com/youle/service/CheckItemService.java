package com.youle.service;

import com.youle.entity.PageResult;
import com.youle.entity.QueryPageBean;
import com.youle.entity.Result;
import com.youle.pojo.CheckItem;

import java.util.List;

//新增检查项业务层接口
public interface CheckItemService {
//    新增检查项
    void add(CheckItem checkItem);
    //    查询
    PageResult pageQuery(QueryPageBean queryPageBean);
//   删除
    void deleteById(Integer checkItemId);

    CheckItem findById(Integer checkItemId);

    void update(CheckItem checkItem);

    List<CheckItem> findAll();
}
