package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.youle.dao.MemberDao;
import com.youle.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName： ReportServiceImpl
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/11/4 20:02
 * @Version: 1.0
 */
@Service(interfaceClass = ReportService.class)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)

public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public List<Integer> findMemberCountByMonth(List<String> month) {
        List<Integer> list = new ArrayList<>();
        for (String m : month) {
            m = m + ".31";//格式：2019.04.31
            Integer count = memberDao.findMemberCountBeforeDate(m);
            list.add(count);
        }
        return list;
    }
}
