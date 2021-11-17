package com.youle.service;

import com.youle.pojo.User;

public interface UserService {
    User findByUserName(String username);
}
