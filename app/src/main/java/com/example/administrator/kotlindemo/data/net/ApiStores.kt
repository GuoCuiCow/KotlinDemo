package com.example.administrator.kotlindemo.data.net

import com.example.administrator.kotlindemo.BuildConfig
import com.example.administrator.kotlindemo.data.entity.WeatherInfoModel
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:网络接口
 */
interface ApiStores {
    companion object {
        //baseUrl
        const val API_SERVER_URL = BuildConfig.API_URL
    }

    //加载天气
    @GET("adat/sk/{cityId}.html")
    fun loadData(@Path("cityId") cityId: String): Observable<WeatherInfoModel>
}