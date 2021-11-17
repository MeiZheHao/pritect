package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.entity.PageResult;
import com.youle.entity.QueryPageBean;
import com.youle.entity.Result;
import com.youle.pojo.CheckGroup;
import com.youle.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName： CheckGroupController
 * @Description: 检查组控制层
 * @Author: 梅哲豪
 * @Date: 2021/10/26 15:30
 * @Version: 1.0
 */
@RequestMapping("/checkGroup")
@RestController
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;
    @RequestMapping("/add.do")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam(required = true, name = "checkitemIds") Integer[] checkItemIds) {
        try {
            checkGroupService.add(checkGroup,checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
//分页查询
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkGroupService.pageQuery(queryPageBean);
        return pageResult;
    }

    //    编辑
    @RequestMapping("/findById.do")
    public Result findById(@RequestParam(required = true, name = "id") Integer checkGroupId) {
        try {
            CheckGroup checkGroup = checkGroupService.findById(checkGroupId);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId.do")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam(required = true, name = "id") Integer checkGroupId) {
        try {
            List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //    修改
    @RequestMapping("/edit.do")
    public Result edit(@RequestBody CheckGroup checkGroup,@RequestParam(required = true, name = "checkItemsIds") Integer[] checkItemsIds) {
        try {
            checkGroupService.edit(checkItemsIds, checkGroup);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    //    删除
    @RequestMapping("/delete.do")
    public Result delete(@RequestParam(required = true, name = "id") Integer checkGroupId) {
        try {
            checkGroupService.deleteById(checkGroupId);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    //    套餐中查询检查组信息
    @RequestMapping("/findAll.do")
    public Result findAll() {
        try {
            List<CheckGroup> checkGroupList = checkGroupService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
}
