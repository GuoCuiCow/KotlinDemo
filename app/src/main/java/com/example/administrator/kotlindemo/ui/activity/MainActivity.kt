package com.example.administrator.kotlindemo.ui.activity


import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.example.administrator.kotlindemo.R
import com.example.administrator.kotlindemo.base.BaseActivity
import com.example.administrator.kotlindemo.data.entity.WeatherInfoModel
import com.example.administrator.kotlindemo.ui.contract.MainContract
import com.example.administrator.kotlindemo.ui.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity(), MainContract.IMainView {
    // 被抽中的卡牌
    private val mCards = intArrayOf(R.mipmap.card_100005,R.mipmap.card_100013,R.mipmap.card_100016,R.mipmap.card_100023,R.mipmap.card_100027,R.mipmap.card_100030,R.mipmap.card_100031,R.mipmap.card_100035)
    private val mPresenter by lazy { MainPresenter() }
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.getWeatherInfo()
        button.setOnClickListener {
            fl.visibility= View.VISIBLE
            biankuang.visibility= View.GONE
            startCard()
        }


    }

    private fun startCard() {
        image.setImageResource(R.drawable.animcard)
        val alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_card)
        image.startAnimation(alphaAnimation)

        val animationDrawable = image.getDrawable() as AnimationDrawable
        animationDrawable.start()
        Observable.just("Amit")
                .delay(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    biankuang.visibility= View.VISIBLE
                    biankuang.setBackgroundResource(R.mipmap.card_bg_1)
                    image.setImageResource(randomCard())
                }, {
                    finish()
                })
    }
    private fun randomCard():Int{
        return mCards[Random().nextInt(8)]
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
