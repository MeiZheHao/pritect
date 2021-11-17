package com.youle.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.dao.CheckItemDao;
import com.youle.entity.PageResult;
import com.youle.entity.QueryPageBean;
import com.youle.pojo.CheckItem;
import com.youle.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName： CheckItemServiceImpl
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/10/25 9:20
 * @Version: 1.0
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;
//   添加方法
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
//  查询方法
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total, rows);
    }
//删除方法
    @Override
    public void deleteById(Integer checkItemId) {
//        不能直接删除因为检查项的id有可能关联检查组的id
        Long countById = checkItemDao.findCountById(checkItemId);
        if (countById > 0) {
            throw new RuntimeException();
        } else {

            checkItemDao.deleteById(checkItemId);
        }
    }
//  修改检查项
    @Override
    public CheckItem findById(Integer checkItemId) {
        return checkItemDao.findById(checkItemId);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
/*
* 检查组相关业务层实现
*
* */
    @Override
    public List<CheckItem> findAll() {

        return checkItemDao.findAll();
    }
}
