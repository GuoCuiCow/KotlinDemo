package com.example.administrator.kotlindemo.ui.model

import com.example.administrator.kotlindemo.data.entity.WeatherInfoModel
import com.example.administrator.kotlindemo.data.net.ApiClient
import com.example.administrator.kotlindemo.ui.contract.MainContract
import rx.Observable

class MainModel : MainContract.IMainModel {
    override val auth: Observable<WeatherInfoModel>
        get() = ApiClient.retrofit()
                .loadData("101190201")

}