package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.Maps;

import java.util.List;
import java.util.Map;

public interface UserService {
    //查询所有的用户，分页查询
    List<User> findAllUser(Integer currentPage, Integer rows);
    //数据个数
    Integer countUser();
    //查询所有用户，不含分页查询
    List<User> findAllUserNotIncludePaging();
    //查询某年每月注册人数人数
    Integer[] findOneYearUser(Integer year);
    //城市男女注册人数
    Map<String,Object> city();
}
