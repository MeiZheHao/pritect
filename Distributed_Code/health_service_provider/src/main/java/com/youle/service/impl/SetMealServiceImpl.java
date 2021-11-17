package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.constant.RedisConstant;
import com.youle.dao.SetMealDao;
import com.youle.entity.PageResult;
import com.youle.entity.QueryPageBean;
import com.youle.pojo.Setmeal;
import com.youle.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName： SetMealServiceImpl
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/10/30 10:23
 * @Version: 1.0
 */
@Service(interfaceClass = SetMealService.class)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealDao setMealDao;
    @Autowired
    private JedisPool jedisPool;
    @Override
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        setMealDao.add(setmeal);
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            Map<String, Integer> map = new HashMap<>();
            for (Integer checkGroupId : checkGroupIds) {
                map.put("setmealId", setmeal.getId());
                map.put("checkGroupId", checkGroupId);
                setMealDao.setSetMealAndGroup(map);
            }
        }
        String filename = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, filename);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> pages = setMealDao.findByCondition(queryString);
        return new PageResult(pages.getTotal(),pages.getResult());
    }

    //    移动端查询套餐信息
    @Override
    public List<Setmeal> getAllSetMeal() {

        return setMealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }
}
