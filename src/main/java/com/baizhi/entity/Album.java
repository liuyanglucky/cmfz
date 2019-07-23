package com.baizhi.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "c_album")
public class Album {
    @Id
    private String id;
    private String title;
    private String cover;
    private Integer count;
    private Double score;
    private String broadcast;
    private String brief;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private String author;

}
