package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.entity.Result;
import com.youle.service.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName： ReportController
 * @Description: echart图的控制层
 * @Author: 梅哲豪
 * @Date: 2021/11/4 19:50
 * @Version: 1.0
 */
//统计报表
@RequestMapping("/report")
@RestController
public class ReportController {
    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport.do")
    public Result getMemberReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);//获得当前日期之前12个月的日期

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            list.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("months", list);

        List<Integer> memberCount = reportService.findMemberCountByMonth(list);
        map.put("memberCount", memberCount);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }
}
