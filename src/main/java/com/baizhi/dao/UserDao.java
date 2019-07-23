package com.baizhi.dao;

import com.baizhi.entity.Maps;
import com.baizhi.entity.User;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {
    //某年某月注册人数
    Integer SomeYearNum(
            @Param("begin") String begin ,
            @Param("end") String end);
    //城市信息
    List<Maps> city(String sex);
}
