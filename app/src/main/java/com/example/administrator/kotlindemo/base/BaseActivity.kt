package com.example.administrator.kotlindemo.base


import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity

import com.example.administrator.kotlindemo.util.TUtil


/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:activity基类
 */
abstract class BaseActivity<P : BasePresenter<*, *>, M : BaseModel?> : AppCompatActivity() {
    val tag = javaClass.simpleName!!
    var mPresenter: P? = null
    var mModel: M? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    /** 初始化  */
    private fun init(savedInstanceState: Bundle?) {

        this.setContentView(this.getLayoutId())
        mPresenter = TUtil.getT<P>(this, 0)
        mModel = TUtil.getT<M>(this, 1)
        if (this is BaseView) mPresenter!!.attachVM(this, mModel)
        initTitle()
        initView(savedInstanceState)
        initEvent()
        initData()
    }

    /** 获取view */
    abstract fun getLayoutId(): Int

    /** 初始化标题]  */
    @CallSuper
    private fun initTitle() {

    }

    /** [Show Toast]  */
    fun showToast(msg: String) {

    }

    /** 初始化view */
    abstract fun initView(savedInstanceState: Bundle?)

    /** 初始化监听事件 */
    protected open fun initEvent() {}

    /** 初始化数据  */
    protected open fun initData() {}

    /** 显示progress */
    protected fun showLoading() {

    }

    /** 自定义progress文本  */
    protected fun showLoading(vararg msg: Int) {

    }

    /** 隐藏progress */
    protected fun hideLoading() {

    }

    override fun onDestroy() {
        mPresenter!!.detachVM()
        super.onDestroy()
    }


}