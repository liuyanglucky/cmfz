package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;

public interface AlbumService {
    //查询所有专辑
    List<Album> findAllAlbum(Integer currentPage, Integer rows);
    //查询专辑数量
    Integer countAlbum();
    //添加专辑
    void addAlbum(Album album);
    //修改专辑
    void updateAlbum(Album album);
}
