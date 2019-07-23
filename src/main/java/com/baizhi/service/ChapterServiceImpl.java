package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Override
    public List<Chapter> findChapterByAlbumId(Integer currentPage, Integer rows , String id) {
        //计算开始第一条
        Integer begin = (currentPage-1)*rows;
        Chapter chapter = new Chapter();
        chapter.setAlbumId(id);
        //分页查询第一条和每页展示个数
        RowBounds rowBounds = new RowBounds(begin, rows);
        //通用mapper方法
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter,rowBounds);
        return chapters;
    }

    @Override
    public Integer countChapter(String id) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(id);
        int count = chapterDao.selectCount(chapter);
        return count;
    }

    @Override
    public void addChapter(Chapter chapter,String id) {
        String uid = UUID.randomUUID().toString();
        Date date = new Date();
        chapter.setId(uid);

        String[] split = id.split(",");
        chapter.setAlbumId(split[0]);
        chapter.setCreateDate(date);
        chapterDao.insert(chapter);
    }

    @Override
    public void updateChapter(Chapter chapter) {
        Date date = new Date();
        chapter.setCreateDate(date);
        chapterDao.updateByPrimaryKeySelective(chapter);
    }
}
