package com.example.administrator.kotlindemo.rx

import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription


/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:初始化
 */
class RxManager {
    private val mCompositeSubscription = CompositeSubscription()// 管理订阅者者

    fun on(action1: Action1<Any>) {
        val mObservable = RxBus.toObservable()
        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(action1))
    }

    fun add(m: Subscription) {
        mCompositeSubscription.add(m)
    }

    fun clear() {
        mCompositeSubscription.unsubscribe()// 取消订阅
    }


}
