package com.youle.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName： Test
 * @Description: java类作用描述
 * @Author: 梅哲豪
 * @Date: 2021/11/4 16:27
 * @Version: 1.0
 */

public class Test {
    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        String admin = "123";
        String encode = bCryptPasswordEncoder.encode(admin);
        System.out.println(encode);
    }
}
