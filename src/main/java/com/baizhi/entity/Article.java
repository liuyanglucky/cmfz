package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_article")
@Document(indexName = "cmfz",type = "article")
public class Article {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;
    //驼峰命名
    private Date createDate;

}
