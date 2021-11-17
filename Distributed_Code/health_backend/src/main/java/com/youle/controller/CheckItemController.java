package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.entity.PageResult;
import com.youle.entity.QueryPageBean;
import com.youle.entity.Result;
import com.youle.pojo.CheckItem;
import com.youle.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName： CheckItemController
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/10/23 11:56
 * @Version: 1.0
 */
@RequestMapping("/checkItem")
@RestController
public class CheckItemController {
    //    注入service服务
    @Reference
    public CheckItemService checkItemService;
//    添加检查项方法
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
    @RequestMapping("/add.do")
//    在前端发起请求的请求体中获取数据
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
//            新增检查项失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
//        新增检查项成功
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
//     查询检查项方法
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//权限校验
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }

    //    删除检查项方法
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @RequestMapping("/delete.do")
    public Result delete(@RequestParam(required = true, name = "id") Integer checkItemId) {
        try {
            checkItemService.deleteById(checkItemId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //    修改方法
    @RequestMapping("/findById.do")
    public Result findById(@RequestParam(required = true, name = "id") Integer checkItemId) {
        CheckItem checkItem = null;
        try {
            checkItem = checkItemService.findById(checkItemId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")//权限校验
    @RequestMapping("/update.do")
    public Result update(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.update(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /*
     * 检查组相关的方法
     * */
//    查询检查项
    @RequestMapping("/findAll.do")
    public Result findAll() {
        List<CheckItem> checkItemList = null;
        try {
            checkItemList = checkItemService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemList);
    }

}
