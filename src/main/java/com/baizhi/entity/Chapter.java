package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_chapter")
public class Chapter {
    @Id
    private String id;
    private String title;
    private String size;
    private String duration;
    private String name;
    //驼峰命名规则
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private String albumId;
}
