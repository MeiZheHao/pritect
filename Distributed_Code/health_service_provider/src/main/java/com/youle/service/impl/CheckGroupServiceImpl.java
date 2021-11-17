package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.dao.CheckGroupDao;
import com.youle.entity.PageResult;
import com.youle.entity.QueryPageBean;
import com.youle.pojo.CheckGroup;
import com.youle.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName： CheckGroupServiceImpl
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/10/26 17:50
 * @Version: 1.0
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupDao.add(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        checkGroupAndItem(checkItemIds, checkGroupId);
    }

    public void checkGroupAndItem(Integer[] checkItemIds, Integer checkGroupId) {
        if (checkItemIds != null && checkItemIds.length > 0) {
            Map<String, Integer> map = new HashMap<>();
            for (Integer checkItemId : checkItemIds) {
                map.put("checkGroupId", checkGroupId);
                map.put("checkItemId", checkItemId);
                checkGroupDao.checkGroupAndCheckItem(map);
            }
        }
    }

    //分页查询
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }
//    编辑
    @Override
    public CheckGroup findById(Integer checkGroupId) {
        return checkGroupDao.findById(checkGroupId);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }
//  修改检查组//        编辑检查组的时候，同时需要关联检查项

    @Override
    public void edit(Integer[] checkItemsIds, CheckGroup checkGroup) {
//        修改检查组基本信息，操作检查组表
        checkGroupDao.edit(checkGroup);
//        清理当前检查组关联的检查项，操作中间表
        checkGroupDao.deleteAssoication(checkGroup.getId());
//        重新建立当前检查组和检查项的关联关系
        Integer checkGroupId = checkGroup.getId();
        checkGroupAndItem(checkItemsIds, checkGroupId);

    }
//删除检查组
    @Override
    public void deleteById(Integer checkGroupId) {
        checkGroupDao.deleteAssoication(checkGroupId);
        checkGroupDao.deleteCheckGroupById(checkGroupId);
    }

    @Override
    public List<CheckGroup> findAll() {

        return checkGroupDao.findAll();
    }
}
