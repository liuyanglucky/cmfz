package com.baizhi.controller;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ChapterDao chapterDao;

    //-----------------------------------------------------------------------------------
    //实现分页查询
    @RequestMapping("allChapter")
    public Map<String, Object> findAllChapter(Integer page, Integer rows, String id) {
        //创建map
        Map<String, Object> map = new HashMap<>();
        //System.out.println("==================================================="+id);
        //查询所有
        List<Chapter> chapters = chapterService.findChapterByAlbumId(page, rows, id);
        //所有个数
        Integer count = chapterService.countChapter(id);
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
        map.put("rows", chapters);//所有章节
        return map;
    }

    //-------------------------------------------------------------------------
    //添加
    @RequestMapping("editChapter")
    public Map<String, Object> editChapter(Chapter chapter, String oper, String aid) {
        //System.out.println("aid=====================" + aid);
        //System.out.println("chapter================" + chapter.getName());
        Map<String, Object> map = new HashMap<>();
        if (oper.equals("add")) {
            chapterService.addChapter(chapter, aid);
            map.put("status", true);
        }
        map.put("message", chapter.getId());
        return map;
    }

    //---------------------------------------------------------------
    //上传实现
    @RequestMapping("upload")
    public void upload(String id, MultipartFile name, HttpServletRequest request) throws IOException, EncoderException {
        Chapter chapter = new Chapter();
        chapter.setId(id);
        name.transferTo(new File(request.getSession().getServletContext().getRealPath("/album/music"), name.getOriginalFilename()));
        //System.out.println("---------------"+new File(request.getSession().getServletContext().getRealPath("/album/music")));
        //System.out.println("-----------------------"+name.getOriginalFilename());
        //计算音乐时长
        Encoder encoder =new  Encoder();
        MultimediaInfo info = encoder.getInfo(new File(request.getSession().getServletContext().getRealPath("/album/music"),name.getOriginalFilename()));
        long duration = info.getDuration();
        chapter.setDuration(duration/1000/60+":"+duration/1000%60);
        //计算文件大小
        BigDecimal size = new BigDecimal(name.getSize());
        BigDecimal dom = new BigDecimal(1024);
        BigDecimal bigDecimal = size.divide(dom).divide(dom).setScale(2, BigDecimal.ROUND_HALF_UP);
        chapter.setSize(bigDecimal+"MB");
        chapter.setName(name.getOriginalFilename());
        chapterService.updateChapter(chapter);
    }
}
