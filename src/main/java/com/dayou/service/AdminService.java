package com.dayou.service;

import com.dayou.pojo.Admin;

/**
 * @Description:
 * @author: dayou
 * @create: 2022-02-19 14:46
 */
public interface AdminService {
    //完成登录判断
    Admin login(String name , String pwd);
}
