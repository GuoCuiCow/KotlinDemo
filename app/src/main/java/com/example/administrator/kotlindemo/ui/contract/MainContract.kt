package com.example.administrator.kotlindemo.ui.contract

import com.example.administrator.kotlindemo.base.BaseModel
import com.example.administrator.kotlindemo.base.BasePresenter
import com.example.administrator.kotlindemo.base.BaseView
import com.example.administrator.kotlindemo.data.entity.WeatherInfoModel
import rx.Observable

/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:
 */
interface MainContract {
    abstract class AuthPresenter : BasePresenter<IMainModel, IMainView>() {
        abstract fun getWeatherInfo()
    }

    interface IMainModel : BaseModel {
        val auth: Observable<WeatherInfoModel>
    }

    interface IMainView : BaseView {
        fun showAuth(bean: WeatherInfoModel)
    }
}