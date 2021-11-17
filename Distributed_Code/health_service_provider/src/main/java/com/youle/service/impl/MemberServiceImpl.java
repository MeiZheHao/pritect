package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.youle.dao.MemberDao;
import com.youle.pojo.Member;
import com.youle.service.MemberService;
import com.youle.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName： MemberServiceImpl
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/11/3 17:54
 * @Version: 1.0
 */
@Service(interfaceClass = MemberService.class)
@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberDao memberDao;
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        String password = member.getPassword();
        if (password != null) {
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberDao.add(member);
    }
}
