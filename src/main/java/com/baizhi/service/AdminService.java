package com.baizhi.service;

import com.baizhi.entity.Admin;

public interface AdminService {
    //根据用户名和密码查询用户
     Admin findAdminByUsernameAndPassword(String username , String password);
}
