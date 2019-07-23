package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @RequestMapping("/login")
    @ResponseBody
    //登陆
    public String adminLogin(String username , String password , String enCode , HttpSession session){
        //根据service查询admin
        Admin admin = adminService.findAdminByUsernameAndPassword(username, password);
       // System.out.println(admin);
        //获取session域中的验证码
        String securityCode = (String) session.getAttribute("securityCode");
       // System.out.println(securityCode);
        //判断验证码是否正确,正确判断admin
        if (securityCode.equalsIgnoreCase(enCode)){
            //System.out.println("进入用户条件判断");
            //判断admin是否为空进行后续操作
            if (admin!=null){
                session.setAttribute("adminName",username);
                //System.out.println("用户判断成功");
                return "ok";
            }else {
                return "false";
            }
        }else {
            return "false";
        }
    }
    //------------------------------------------------------------------------
    @RequestMapping("/exit")
    //安全退出
    public String exit(HttpSession session){
        session.removeAttribute("adminName");
        return "redirect:/login/login.jsp";
    }
}
