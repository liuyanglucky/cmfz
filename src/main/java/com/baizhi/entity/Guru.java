package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_guru")
public class Guru {
    @Id
    private String id;
    private String name;
    private String photo;
    private String status;
    private String sex;
}
