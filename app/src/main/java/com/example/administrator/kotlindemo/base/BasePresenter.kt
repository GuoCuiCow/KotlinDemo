package com.example.administrator.kotlindemo.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:
 */
open class BasePresenter<T : IBaseView>  : IPresenter<T>{

    var mRootView: T? = null
        private set
    private var compositeSubscription = CompositeSubscription()

    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null

        //保证activity结束时取消所有正在执行的订阅
        compositeSubscription.unsubscribe()


    }

    private val isViewAttached: Boolean
        get() = mRootView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    fun addSubscription(m: Subscription?) {
        compositeSubscription.add(m)
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")


}