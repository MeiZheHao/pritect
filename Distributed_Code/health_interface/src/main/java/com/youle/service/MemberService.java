package com.youle.service;

import com.youle.pojo.Member;

public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);
}
