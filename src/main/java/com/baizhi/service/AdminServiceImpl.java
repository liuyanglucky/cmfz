package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
@Autowired
private AdminDao adminDao;
    @Override
    public Admin findAdminByUsernameAndPassword(String username, String password) {
        Admin one = new Admin(null,username,password,null);
        Admin admin = adminDao.selectOne(one);
        return admin;
    }
}
