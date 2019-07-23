package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;

public interface BannerService {
    //查询所有的轮播图
    List<Banner> findAllBanner(Integer currentPage, Integer rows);
    //查询总条数
    Integer countBanner();
    //添加轮播图
    void addBanner(Banner banner);
    //修改轮播图
    void updateBanner(Banner banner);
    //删除轮播图
    void delBanner(String id);
}
