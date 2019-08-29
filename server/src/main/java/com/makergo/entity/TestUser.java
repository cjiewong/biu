package com.makergo.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@Data
public class TestUser {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private Date created;
}
