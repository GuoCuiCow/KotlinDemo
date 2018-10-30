package com.example.administrator.kotlindemo.base

import com.example.administrator.kotlindemo.rx.RxManager

/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:
 */
abstract class BasePresenter<M, V> {

    var mModel: M? = null
    var mView: V? = null
    var mRxManager = RxManager()

    fun attachVM(v: V, m: M?) {
        this.mView = v
        this.mModel = m
        this.onStart()
    }

    fun detachVM() {
        mRxManager.clear()
        mView = null
        mModel = null
    }

    abstract fun onStart()
}