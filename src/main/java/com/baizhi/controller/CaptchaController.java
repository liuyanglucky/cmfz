package com.baizhi.controller;

import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/code")
public class CaptchaController{
    //获取验证码
    @RequestMapping("/getCode")
    public String getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        //1.获取验证码随机数
        String securityCode = SecurityCode.getSecurityCode();
        //放入session域中
        session.setAttribute("securityCode",securityCode);
        //2.获取验证码图片
        BufferedImage image = SecurityImage.createImage(securityCode);
        //3.将验证码图片响应到客户端
        ServletOutputStream out = response.getOutputStream();
        //调用工具类
        ImageIO.write(image,"png",out);
        //返回null不跳转
        return null;
    }
}
