package com.example.administrator.kotlindemo.ui.activity


import android.os.Bundle
import com.example.administrator.kotlindemo.R
import com.example.administrator.kotlindemo.base.BaseActivity
import com.example.administrator.kotlindemo.data.entity.WeatherInfoModel
import com.example.administrator.kotlindemo.ui.contract.MainContract
import com.example.administrator.kotlindemo.ui.presenter.MainPresenter

class MainActivity : BaseActivity(), MainContract.IMainView {

    private val mPresenter by lazy { MainPresenter() }
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.getWeatherInfo()
    }


    override fun showAuth(bean: WeatherInfoModel) {

    }

    override fun showError(msg: String) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
