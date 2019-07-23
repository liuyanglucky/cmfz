package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Data//lombok中用于getter、setter、toString等
@AllArgsConstructor//lombok中有参构造
@NoArgsConstructor//lombok中无参构造
@Table(name = "c_admin")//实体类与表名不相同时，声明对应关系
public class Admin {
    @Id//用于声明哪一个是主键
    private Integer id;

    private String username;
    private String password;
    private String nickname;
}
