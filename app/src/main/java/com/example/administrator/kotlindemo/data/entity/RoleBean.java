package com.example.administrator.kotlindemo.data.entity;

/**
 * author: CuiGuo
 * date: 2018/11/15
 * info:用户属性
 */
public class RoleBean implements java.io.Serializable{

    public RoleBean(int id) {
        this.id = id;
    }

    public int id;//id
    public String name;//姓名
    public String avatar;//头像
}
