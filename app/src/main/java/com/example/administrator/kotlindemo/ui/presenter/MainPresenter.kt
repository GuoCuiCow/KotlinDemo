package com.example.administrator.kotlindemo.ui.presenter


import com.example.administrator.kotlindemo.data.entity.WeatherInfoModel
import com.example.administrator.kotlindemo.data.net.ApiCallback
import com.example.administrator.kotlindemo.ui.contract.MainContract
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainPresenter : MainContract.AuthPresenter() {
    override fun getWeatherInfo() {
        //object为对象表达式
        mRxManager.add(mModel
                ?.auth
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : ApiCallback<WeatherInfoModel>() {
                    override fun onSuccess(model: WeatherInfoModel) {

                    }

                    override fun onFailure(msg: String?) {

                    }

                    override fun onFinish() {

                    }
                })!!)
    }

    override fun onStart() {

    }
}