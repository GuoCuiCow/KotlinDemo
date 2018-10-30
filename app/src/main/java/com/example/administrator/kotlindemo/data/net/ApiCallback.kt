package com.example.administrator.kotlindemo.data.net

import android.util.Log
import retrofit2.HttpException
import rx.Subscriber

/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:网络返回的回调
 */
abstract class ApiCallback<M> : Subscriber<M>() {
    private val tag: String?
        get() = this::class.simpleName

    abstract fun onSuccess(model: M)
    abstract fun onFailure(msg: String?)
    abstract fun onFinish()
    override fun onCompleted() {
        onFinish()
    }

    override fun onNext(m: M) {
        onSuccess(m)
    }

    override fun onError(e: Throwable?) {
        //这块应该可以优化
        if (e is HttpException) {
            //httpException.response().errorBody().string()
            val code = e.code()
            var msg = e.message
            Log.e(tag, "code=$code")
            if (code == 504) {
                msg = "网络不给力"
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试"
            }
            onFailure(msg)
        } else {
            onFailure(e.toString())
        }
        onFinish()
    }
}