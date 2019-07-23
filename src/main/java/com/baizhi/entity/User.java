package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_user")
public class User {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "联系方式")
    private String phone;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "用户密码")
    private String password;
    @Excel(name = "密码盐")
    private String salt;
    @Excel(name = "法名")
    private String dharma;
    @Excel(name = "省份")
    private String province;
    @Excel(name = "城市")
    private String city;
    @Excel(name = "宣言")
    private String sign;
    @Excel(name = "照片",type = 2)
    private String photo;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "创建日期",format = "yyyy-MM-dd HH:mm:ss")
    //驼峰命名
    private Date createDate;
}
