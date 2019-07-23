package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    //---------------------------------------------------------
    //查询所有的轮播图片
    @RequestMapping("/allBanner")
    @ResponseBody
    public Map<String,Object> findAllBanner(Integer page, Integer rows){
        //创建map集合存储所有参数
        Map<String, Object> map = new HashMap<String,Object>();
        //所有的Banner
        List<Banner> banners = bannerService.findAllBanner(page,rows);
        /*for (Banner banner : banners) {
            System.out.println(banner);
        }*/
        //Banner的个数
        Integer count = bannerService.countBanner();
        //总页数
        Integer total = null;
        if (count%rows==0){
            total = count/rows;
        }else {
            total = count/rows+1;
        }
        map.put("page",page);// 当前页码
        map.put("total",total); // 总页数
        map.put("records",count);// 总条数
        map.put("rows", banners);//所有轮播图
        return map;
    }
    //-------------------------------------------------
    //增删改
    @RequestMapping("/editBanner")
    @ResponseBody
    public Map<String,Object> editBanner(String oper,Banner banner,String id){
        Map<String,Object> map = new HashMap<>();
        if (oper.equals("add")){
            bannerService.addBanner(banner);
            map.put("status",true);
        }else if(oper.equals("edit")){
            if ("".equals(banner.getCover())){
                banner.setCover(null);
            }
            bannerService.updateBanner(banner);
            map.put("status",true);
        }else if(oper.equals("del")){
            bannerService.delBanner(id);
            map.put("status",true);
        }else {
            map.put("status",false);
        }
        map.put("message",banner.getId());
        return map;
    }
    //----------------------------------------------------------
    //文件上传
    @RequestMapping("/upload")
    @ResponseBody
    public void upload(String id, MultipartFile cover, HttpServletRequest request) throws IOException {
        //System.out.println("id:"+id);
//        文件上传
//        String filename = cover.getOriginalFilename();
//        ServletContext servletContext = request.getSession().getServletContext();
//        String realPath = servletContext.getRealPath("/banner/ing");
        cover.transferTo(new File(request.getSession().getServletContext().getRealPath("/banner/img"),cover.getOriginalFilename()));
       // System.out.println("cover upload over!");
//        根据id修改图片的名字
        Banner banner = new Banner();
        banner.setId(id);
        banner.setCover(cover.getOriginalFilename());
        bannerService.updateBanner(banner);
    }
}
