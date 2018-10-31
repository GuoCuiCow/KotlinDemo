package com.example.administrator.kotlindemo.ui.presenter


import com.example.administrator.kotlindemo.data.entity.WeatherInfoModel
import com.example.administrator.kotlindemo.data.net.ApiCallback
import com.example.administrator.kotlindemo.ui.contract.MainContract
import com.example.administrator.kotlindemo.ui.model.MainModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainPresenter : MainContract.AuthPresenter() {

    private val mModel: MainModel by lazy {

        MainModel()
    }

    override fun getWeatherInfo() {
        //object为对象表达式
        val subscribe = mModel.auth
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiCallback<WeatherInfoModel>() {
                    override fun onSuccess(model: WeatherInfoModel) {

                    }

                    override fun onFailure(msg: String?) {

                    }

                    override fun onFinish() {

                    }
                })
        addSubscription(subscribe)
    }

}