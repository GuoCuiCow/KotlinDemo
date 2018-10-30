package com.example.administrator.kotlindemo.ui.activity

import android.os.Bundle
import com.example.administrator.kotlindemo.R
import com.example.administrator.kotlindemo.base.BaseActivity
import com.example.administrator.kotlindemo.data.entity.WeatherInfoModel
import com.example.administrator.kotlindemo.data.net.ApiCallback
import com.example.administrator.kotlindemo.data.net.ApiClient

class MainActivity : BaseActivity() {
    //问号表示该变量可以为空
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //object为对象表达式
        addSubscription(ApiClient
                .retrofit()
                .loadData("101190201"),
                object : ApiCallback<WeatherInfoModel>() {
                    override fun onSuccess(model: WeatherInfoModel) {
                        log("""city=${model.weatherinfo.city},cityid=${model.weatherinfo.cityid}""")//输出“city=无锡,cityid=101190201”
                    }

                    override fun onFailure(msg: String?) {
                        log("onFailure=$msg")
                    }

                    override fun onFinish() {
                        log("onFinish")
                    }
                })
    }


}
