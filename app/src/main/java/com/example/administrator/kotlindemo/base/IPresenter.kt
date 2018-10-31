package com.example.administrator.kotlindemo.base

/**
 * author: CuiGuo
 * date: 2018/10/31
 * info:
 */
interface IPresenter<in V: IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}