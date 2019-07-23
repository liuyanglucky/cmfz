package com.baizhi.service;


import com.baizhi.entity.Chapter;

import java.util.List;

public interface ChapterService {
    //查询所有专辑id的章节实现分页
    List<Chapter> findChapterByAlbumId(Integer currentPage, Integer rows ,String id);
    //查询章节数量
    Integer countChapter(String id);
    //添加章节
    void addChapter(Chapter chapter,String id);
    //修改章节
    void updateChapter(Chapter chapter);
}
