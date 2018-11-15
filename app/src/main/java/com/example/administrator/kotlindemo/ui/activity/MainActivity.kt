package com.example.administrator.kotlindemo.ui.activity


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.animation.AnimationUtils
import com.example.administrator.kotlindemo.R
import com.example.administrator.kotlindemo.base.BaseActivity

import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * author: CuiGuo
 * date: 2018/11/15
 * info:抽奖页面
 */
class MainActivity : BaseActivity() {
    // 被抽中的卡牌
    private val mCards = intArrayOf()
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        verifyStoragePermissions(this)
        button.setOnClickListener {
            var intent = Intent()
            intent.setClass(this@MainActivity, RolePoolActivity::class.java)
            startActivity(intent)

//            fl.visibility= View.VISIBLE
//            biankuang.visibility= View.GONE
//            startCard()
        }


    } 

    private fun startCard() {
        image.setImageResource(R.drawable.animcard)
        val alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_card)
        image.startAnimation(alphaAnimation)
        image.drawable
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
        return mCards[Random().nextInt(mCards.size)]
    }


    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")


    private fun verifyStoragePermissions(activity: Activity) {

        try {
            //检测是否有写的权限
            val permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE")
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
