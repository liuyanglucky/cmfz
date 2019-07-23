package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.entity.Maps;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public List<User> findAllUser(Integer currentPage, Integer rows) {
        //计算本页开始的第一条
        Integer begin = (currentPage-1)*rows;
        //实现本页分页功能              开始位置   每页展示个数
        RowBounds rowBounds = new RowBounds(begin,rows);
        //创建user对象
        User user = new User();
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        return users;
    }

    @Override
    public Integer countUser() {
        User user = new User();
        int count = userDao.selectCount(user);
        return count;
    }

    @Override
    public List<User> findAllUserNotIncludePaging() {
        List<User> users = userDao.selectAll();
        return users;
    }
    //-----------------------------------------------------------------------------
    //查询某年每月注册人数人数
    @Override
    public Integer[] findOneYearUser(Integer year) {
       //常识可知：1、3、5、7、8、10、12月均为31天
        // 2月有闰年平年之分，闰年2月29天
        Integer[] mouth = new Integer[12];
        for (int i = 1; i <=12 ; i++) {
            if (i==1||i==3||i==5||i==7||i==8||i==10||i==12){
                String begin = year+"-0"+i+"-01";
                String end = year+"-0"+i+"-31";
                Integer count = userDao.SomeYearNum(begin, end);
                mouth[i-1]=count;
            }else if (i==2){
                //再次判断是否为闰年
                if (year%400==0){
                    //为润年
                    String begin = year+"-0"+2+"-01";
                    String end = year+"-0"+2+"-29";
                    Integer count = userDao.SomeYearNum(begin, end);
                    mouth[1]=count;
                }else if (year%4==0&&year%100!=0){
                    //为闰年
                    String begin = year+"-0"+2+"-01";
                    String end = year+"-0"+2+"-29";
                    Integer count = userDao.SomeYearNum(begin, end);
                    mouth[1]=count;
                }else {
                    //为平年
                    String begin = year+"-0"+2+"-01";
                    String end = year+"-0"+2+"-28";
                    Integer count = userDao.SomeYearNum(begin, end);
                    mouth[1]=count;
                }
            }else {
                String begin = year+"-0"+i+"-01";
                String end = year+"-0"+i+"-30";
                Integer count = userDao.SomeYearNum(begin, end);
                mouth[i-1]=count;
            }
        }
        return mouth;
    }
    //----------------------------------------------------------------------
    //城市中男女个数
    @Override
    public Map<String,Object> city() {
        Map<String,Object> map = new HashMap<>();
        List<Maps> listm = userDao.city("男");
        map.put("male",listm);
        List<Maps> listf = userDao.city("女");
        map.put("fmale",listf);
        return map;
    }
}
