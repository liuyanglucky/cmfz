package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Override
    public List<Album> findAllAlbum(Integer currentPage, Integer rows) {
        //计算本页开始开始第一条
        Integer begin = (currentPage-1)*rows;
        Album album = new Album();
        //分页开始第一条，与每条展示个数
        RowBounds rowBounds = new RowBounds(begin, rows);
        List<Album> albums = albumDao.selectByRowBounds(album,rowBounds);
        return albums;
    }

    @Override
    public Integer countAlbum() {
        Album album = new Album();
        int count = albumDao.selectCount(album);
        return count;
    }

    @Override
    public void addAlbum(Album album) {
        String id = UUID.randomUUID().toString();
        album.setId(id);
        Date date = new Date();
        album.setCreateDate(date);
        albumDao.insert(album);
    }

    @Override
    public void updateAlbum(Album album) {
        Date date = new Date();
        album.setCreateDate(date);
        albumDao.updateByPrimaryKeySelective(album);
    }
}
