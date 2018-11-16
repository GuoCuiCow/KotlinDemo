package com.example.administrator.kotlindemo.ui.activity


import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.example.administrator.kotlindemo.R
import com.example.administrator.kotlindemo.base.BaseActivity
import com.example.administrator.kotlindemo.data.entity.MarryBean
import com.example.administrator.kotlindemo.data.entity.RoleBean
import com.example.administrator.kotlindemo.ui.adapter.MarryAdapter
import com.example.administrator.kotlindemo.ui.adapter.RoleAdapter
import com.example.administrator.kotlindemo.util.ACache
import com.example.administrator.kotlindemo.util.PhoneUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

    private val marryBeans = ArrayList<MarryBean>()
    val roleBeans = ArrayList<RoleBean>()
    private var index: Int = 0
    var sender: RoleBean? = null
    var receiver: RoleBean? = null
    lateinit var options: RequestOptions
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        //初始化列表
        val mRecyclerView = findViewById<RecyclerView>(R.id.rv_married)
//设置布局管理器
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        mRecyclerView.layoutManager = layoutManager
//设置adapter
        val adapter = MarryAdapter(mRecyclerView)
        adapter.setRoles(marryBeans)
        mRecyclerView.adapter = adapter
//设置Item增加、移除动画
        mRecyclerView.itemAnimator = DefaultItemAnimator()

        fl.setOnClickListener {
            fl.visibility = View.GONE
            biankuang.visibility = View.GONE
            rv_married.visibility = View.VISIBLE
            adapter.notifyDataSetChanged()
        }

        options = RequestOptions()
                .centerCrop()
                .error(R.mipmap.error)
                .priority(Priority.HIGH)
        verifyStoragePermissions(this)
        button.setOnClickListener {
            if (roleBeans.size>0){
                marry()
            }

        }
        add.setOnClickListener {
            var intent = Intent()
            intent.setClass(this@MainActivity, RolePoolActivity::class.java)
            startActivityForResult(intent, 1)
        }


    }

    //点击匹配
    private fun marry() {

        if (index > roleBeans.size) {
            showToast("已经全部匹配完毕")
        } else {
            index++
            when (index) {
                1 -> {
                    receiver = roleBeans[index - 1]
                    startCard(receiver!!)
                }
                roleBeans.size+1 -> {
                    sender = receiver
                    receiver = roleBeans[0]
                    marryBeans.add(MarryBean(sender, receiver))
                    startCard(receiver!!)
                }
                else -> {
                    sender = receiver
                    receiver = roleBeans[index - 1]
                    marryBeans.add(MarryBean(sender, receiver))
                    startCard(receiver!!)
                }
            }
        }


    }

    private fun startCard(role: RoleBean) {
        fl.visibility = View.VISIBLE
        rv_married.visibility = View.GONE
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
                    biankuang.visibility = View.VISIBLE
                    biankuang.setBackgroundResource(R.mipmap.card_bg_1)

                    Glide.with(this@MainActivity).load(role.avatar).apply(options).into(image)

                }, {
                    finish()
                })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }else{
                finish()
            }
        }
    }
    override fun initData() {
        super.initData()


        val gson = Gson()
        val mCache: ACache = ACache.get(this)
        val value = mCache.getAsString("role_list")

        val roles = gson.fromJson<List<RoleBean>>(value, object : TypeToken<List<RoleBean>>() {

        }.type)
        if (roles != null) {
            roleBeans.clear()
            roleBeans.addAll(roles)
        }
        if (roleBeans.size < 2) {
            showToast("请添加更多活动成员")
        }

        roleBeans.shuffle()//乱序


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        initData()
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
