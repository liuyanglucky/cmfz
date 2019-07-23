package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data//lombok中用于getter、setter、toString等
@AllArgsConstructor//lombok中有参构造
@NoArgsConstructor//lombok中无参构造
@Table(name = "c_banner")//实体类与表名不相同时，声明对应关系
public class Banner {
    @Id//用于声明哪一个是主键
    private String id;
    private String name;
    private String cover;
    private String description;
    private String status;
    private Date create_date;
}
