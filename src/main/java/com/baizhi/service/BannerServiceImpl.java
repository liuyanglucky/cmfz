package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.redisCache.RedisCache;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;
    //-----------------------------------------------
    //查询所有，包含分页
    /**
     *
     * @param currentPage  当前页码
     * @param rows  每页展示的条数
     * @return
     */
    @Override
    @RedisCache
    public List<Banner> findAllBanner(Integer currentPage, Integer rows) {
        //计算本页开始开始第一条
        Integer begin = (currentPage-1)*rows;
        Banner banner = new Banner();
        //分页开始第一条，与每条展示个数
        RowBounds rowBounds = new RowBounds(begin, rows);
        List<Banner> banners = bannerDao.selectByRowBounds(banner, rowBounds);
        return banners;
    }
    //------------------------------------------------------------
    //查询总条数
    @Override
    public Integer countBanner() {
        Banner banner = new Banner();
        Integer count = bannerDao.selectCount(banner);
        return count;
    }
    //------------------------------------------------------------
    //添加
    @Override
    public void addBanner(Banner banner) {
        String id = UUID.randomUUID().toString();
        banner.setId(id);
        Date date = new Date();
        banner.setCreate_date(date);
        bannerDao.insert(banner);
    }
    //---------------------------------------------
    //修改
    @Override
    public void updateBanner(Banner banner) {
        Date date = new Date();
        banner.setCreate_date(date);
        bannerDao.updateByPrimaryKeySelective(banner);
    }
    //-------------------------------------------------
    //删除
    @Override
    public void delBanner(String id) {
        bannerDao.deleteByPrimaryKey(id);
    }
}
