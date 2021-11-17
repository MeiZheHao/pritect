package com.youle.dao;


import com.github.pagehelper.Page;
import com.youle.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
//    添加
    void add(CheckItem checkItem);
 //   查询
    Page<CheckItem> selectByCondition(String queryString);
//    删除
    void deleteById(Integer checkItemId);

    //    查询检查项的id是否和检查组关联，关联则无法删除
    Long findCountById(Integer checkItemId);

    CheckItem findById(Integer checkItemId);

    void update(CheckItem checkItem);

    List<CheckItem> findAll();

    List<CheckItem> findCheckItemById(Integer checkGroupId);
}
