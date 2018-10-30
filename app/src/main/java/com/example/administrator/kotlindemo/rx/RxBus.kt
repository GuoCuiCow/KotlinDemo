package com.example.administrator.kotlindemo.rx


import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject


/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:基于rx的事件总线
 */

object RxBus {
    private val mBus = SerializedSubject(PublishSubject.create<Any>())

    fun <T> toObservable(clzz: Class<T>): Observable<T> = mBus.ofType(clzz)

    fun toObservable(): Observable<Any> = mBus

    fun post(obj: Any) {
        mBus.onNext(obj)
    }

    fun hasObservers(): Boolean {
        return mBus.hasObservers()
    }
}

