package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    //--------------------------------------------------------------
    //查询所有文章，包含分页功能
    @RequestMapping("allArticle")
    public Map<String,Object> findAllArticle(Integer page , Integer rows){
        //创建map
        Map<String, Object> map = new HashMap<>();
        //查询所有
        List<Article> articles = articleService.findAllArticle(page, rows);
        //个数
        Integer count = articleService.countArticle();
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
        map.put("rows", articles);//所有专辑
        return map;
    }
    //-------------------------------------------------------------------------------
    //文件上传的实现
    @RequestMapping("upload")
    public Map<String,Object> upload(HttpServletRequest request, MultipartFile articleImage) {
        Map<String, Object> map = new HashMap<>();
    //文件上传
        try {
            articleImage.transferTo(new File(request.getSession().getServletContext().getRealPath("image"),articleImage.getOriginalFilename()));
            map.put("error",0);
            map.put("url","http://localhost:8989/image/"+articleImage.getOriginalFilename());
        } catch (IOException e) {
            map.put("error",1);
        }
        return map;
    }
    //-----------------------------------------------------------------------
    ////图片空间对应的地址
    @RequestMapping("browser")
    public Map<String,Object> browser(HttpServletRequest request){
        File file = new File(request.getSession().getServletContext().getRealPath("image"));
        File[] files = file.listFiles();
        Map<String, Object> map = new HashMap<>();
        //文件夹的网络路径
        map.put("current_url","http://localhost:8989/image/");
        //当前文件夹中的文件的数量
        map.put("total_count",files.length);
        ArrayList<Object> list = new ArrayList<>();
        for (File img : files) {
            HashMap<String, Object> imgObject = new HashMap<>();
            imgObject.put("is_dir",false);
            imgObject.put("has_file",false);
            imgObject.put("filesize",img.length());
            imgObject.put("is_photo",true);
            imgObject.put("filetype", FilenameUtils.getExtension(img.getName()));
            imgObject.put("filename", img.getName());
            imgObject.put("datetime", "2018-06-06 00:36:39");
            list.add(imgObject);
        }
        map.put("file_list",list);
        return map;
    }
    //---------------------------------------------------------
    //增加、删除、修改
    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Article article){
        //System.out.println("基本信息测试-------------------"+article);
        Map<String, Object> map = new HashMap<>();
        try {
            if("add".equals(oper)){
                articleService.add(article);
            }
            if("edit".equals(oper)){
                articleService.edit(article);
            }
            if("del".equals(oper)){
                articleService.del(article);
            }
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
}
