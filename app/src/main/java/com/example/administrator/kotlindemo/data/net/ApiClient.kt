package com.example.administrator.kotlindemo.data.net

import com.example.administrator.kotlindemo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:网络请求封装
 */
//object声明单例模式
object ApiClient {
    fun retrofit(): ApiStores {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor)
        }
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
                .baseUrl(ApiStores.API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        //ApiStores::class.java取得对象的 Java 类
        return retrofit.create(ApiStores::class.java)
    }
}