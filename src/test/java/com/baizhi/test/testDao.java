package com.baizhi.test;

import com.baizhi.dao.*;
import com.baizhi.entity.*;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testDao {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private GuruDao guruDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao;
    @Test
    public void Test1(){
        List<Admin> admins = adminDao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }
    @Test
    public void Test2(){
        Admin admin = new Admin(null,"admin","admin",null);
        Admin one = adminDao.selectOne(admin);
        System.out.println(one);
    }
    @Test
    public void Test3(){
        List<Banner> banners = bannerDao.selectAll();
        for (Banner banner : banners) {
            System.out.println(banner);
        }
    }
    //专辑测试
    @Test
    public void Test4(){
        List<Album> albums = albumDao.selectAll();
        for (Album album : albums) {
            System.out.println(album);
        }
    }
    @Test
    public void Test5(){
        Album album = new Album();
        //分页开始第一条，与每条展示个数
        RowBounds rowBounds = new RowBounds(1, 5);
        List<Album> albums = albumDao.selectByRowBounds(album, rowBounds);
//        List<Album> albums = albumDao.selectByExampleAndRowBounds(album, rowBounds);
        for (Album album1 : albums) {
            System.out.println(album1);
        }
    }
    @Test
    public void Test6(){
        RowBounds rowBounds = new RowBounds(0, 10);
        //通用mapper方法
        Chapter chapter = new Chapter();
        chapter.setAlbumId("1");
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter,rowBounds);
        for (Chapter chapter1 : chapters) {
            System.out.println(chapter1);
        }
    }
    @Test
    public void Test7(){
        List<Guru> gurus = guruDao.selectAll();
        for (Guru guru : gurus) {
            System.out.println(guru);
        }
    }
    @Test
    public void Test8(){
        List<Article> articles = articleDao.selectAll();
        for (Article article : articles) {
            System.out.println(article);
        }
    }
    @Test
    public void Test9(){
        List<User> users = userDao.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void Test10(){
        Integer integer = userDao.SomeYearNum("2019-01-01", "2019-01-31");
        System.out.println(integer);
    }
}
