package com.abc.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TestUser {
    private Integer id;
    private String name;
    private Integer age;
    private Date created;
}
