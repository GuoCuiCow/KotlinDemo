package com.example.administrator.kotlindemo.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:activity基类
 */
open class BaseActivity : AppCompatActivity() {
    val tag = this::class.simpleName
    val mCompositeSubscription: CompositeSubscription = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        if (mCompositeSubscription.hasSubscriptions()) {
            //取消注册，以避免内存泄露
            mCompositeSubscription.unsubscribe()
        }
        super.onDestroy()
    }

    open fun <M> addSubscription(observable: Observable<M>, subscriber: Subscriber<M>) {
        mCompositeSubscription.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber))
    }

    open fun log(msg: String) {
        Log.e(tag, msg)
    }
}