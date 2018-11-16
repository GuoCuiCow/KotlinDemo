package com.example.administrator.kotlindemo.data.entity;

/**
 * author: CuiGuo
 * date: 2018/11/16
 * info:
 */
public class MarryBean {
    public MarryBean(RoleBean sender, RoleBean receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public RoleBean sender;
    public RoleBean receiver;
}
