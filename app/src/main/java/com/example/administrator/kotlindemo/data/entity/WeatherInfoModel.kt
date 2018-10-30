package com.example.administrator.kotlindemo.data.entity

/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:天气信息实体类
 */
data class WeatherInfoModel constructor(val weatherinfo: WeatherInfoBean) {
    data class WeatherInfoBean(
            val city: String,
            val cityid: String
    )
}