package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/allUser")
    public Map<String, Object> findAllUser(Integer page, Integer rows) {
        //System.out.println("进入到方法当中");
        //创建map
        Map<String, Object> map = new HashMap<>();
        //查询所有
        List<User> allUser = userService.findAllUser(page, rows);
        //个数
        Integer count = userService.countUser();
        //总页数
        Integer total = null;
        if (count % rows == 0) {
            total = count / rows;
        } else {
            total = count / rows + 1;
        }
        map.put("page", page);// 当前页码
        map.put("total", total); // 总页数
        map.put("records", count);// 总条数
        map.put("rows", allUser);//所有专辑
        return map;
    }

    //----------------------------------------------------------------------------------------
    //easyPOI的使用把数据导出
    @RequestMapping("out")
    public void out(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //查询所有用户
        List<User> allUsers = userService.findAllUserNotIncludePaging();
        //easyPOI
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户详情信息表", "用户表"), User.class, allUsers);
        String fileName = "用户报表(" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ").xls";

        fileName = new String(fileName.getBytes("gbk"), "iso-8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        workbook.write(response.getOutputStream());

    }

    //------------------------------------------------------------------------------------------------
    //echars 先后台得到每个月的注册人数数据
    @RequestMapping("mouth")
    public Map<String, Object> findEveryMouth() {

        Map<String, Object> map = new HashMap<>();
        List<Integer>list=new ArrayList<>();
        //当前系统时间
        Date date = new Date();
        /**
         * 创建格式化时间日期类
         *构造入参String类型就是我们想要转换成的时间形式
         */
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = format.format(date);
        String substring = sDate.substring(0, 4);
        int year = Integer.parseInt(substring);
        //System.out.println("year----------" + year);
        Integer[] mouth = userService.findOneYearUser(year);
        for (Integer integer : mouth) {
             list.add(integer);
    }
    map.put("data",list);
        return map;
    }
    //-------------------------------------------------------------------------------------
    //城市中男女个数
    @RequestMapping("map")
    public Map<String,Object> city(){
        Map<String, Object> city = userService.city();
//        Set<String> keys = city.keySet();
//        for (String key : keys) {
//            List<Maps> ma = (List<Maps>) city.get(key);
//            for (Maps maps : ma) {
//                System.out.println(key+"---------------"+maps);
//            }
//        }
        return city;
    }
}
