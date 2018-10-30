package com.example.administrator.kotlindemo.base

/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:
 */
interface BaseView {
    fun showError(msg: String)
    fun showProgress() //显示加载图
    fun hideProgress() //隐藏加载图
}