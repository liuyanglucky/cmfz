package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    //-----------------------------------------
    //带有分页功能的查询所有
    @RequestMapping("allAlbum")
    public Map<String,Object> findAllAlbum(Integer page, Integer rows){
        //创建map集合
        Map<String, Object> map = new HashMap<>();
        //查询所有
        List<Album> albums = albumService.findAllAlbum(page, rows);
        //所有的个数
        Integer count = albumService.countAlbum();
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
        map.put("rows", albums);//所有专辑
        return map;
    }
    //--------------------------------------
    //添加功能的实现
    @RequestMapping("editAlbum")
    public  Map<String,Object> editAlbum(Album album,String oper){
        Map<String,Object> map = new HashMap<>();
        if (oper.equals("add")) {
            albumService.addAlbum(album);
            map.put("status",true);
        }
        map.put("message",album.getId());
        return map;
    }
    //----------------------------------------
    //上传实现
    @RequestMapping("upload")
    public void upload(String id, MultipartFile cover, HttpServletRequest request) throws IOException {
        //System.out.println("id:"+id);
//        文件上传
//        String filename = cover.getOriginalFilename();
//        ServletContext servletContext = request.getSession().getServletContext();
//        String realPath = servletContext.getRealPath("/banner/ing");
        cover.transferTo(new File(request.getSession().getServletContext().getRealPath("/album/img"),cover.getOriginalFilename()));
        // System.out.println("cover upload over!");
//        根据id修改图片的名字
        Album album = new Album();
        album.setId(id);
        album.setCover(cover.getOriginalFilename());
        albumService.updateAlbum(album);
    }
}
