package com.dayou.controller;

import com.dayou.mapper.AdminMapper;
import com.dayou.pojo.Admin;
import com.dayou.service.AdminService;
import com.dayou.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @author: dayou
 * @create: 2022-02-19 16:41
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {
    //所有界面层,一定会有业务逻辑层的对象

    @Autowired
    AdminService adminService;

    //实现登录判断,并进行相应的跳转
    @RequestMapping("/login")
    public String login(String name,String pwd, HttpServletRequest request){

        Admin admin =adminService.login(name,pwd);

        if(admin !=null){
            //登录成功
            request.setAttribute("admin",admin);
            return "main";
        }else{
            //登录失败
            request.setAttribute("errmsg","用户名或密码不正确!");
            return "login";
        }
    }

}
