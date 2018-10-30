package com.example.administrator.kotlindemo.util


import android.util.Log

import com.example.administrator.kotlindemo.BuildConfig


/**
 * author: CuiGuo
 * date: 2018/10/30
 * info:统一的日志输出类，可以控制debug或者release的apk是否输出日志信息
 */
object LogUtils {
    val DEBUG = BuildConfig.DEBUG

    /**
     * 根据type输出日志消息，包括方法名，方法行数，Message
     *
     * @param type
     * @param message
     */
    private fun log(type: Int, message: String) {
        var message = message
        val stackTrace = Thread.currentThread().stackTrace[4]
        val className = stackTrace.className
        val tag = className.substring(className.lastIndexOf('.') + 1)
        message = (stackTrace.methodName + "#" + stackTrace.lineNumber + " [" + message
                + "]")
        when (type) {
            Log.DEBUG -> Log.d(tag, message)
            Log.INFO -> Log.i(tag, message)
            Log.WARN -> Log.w(tag, message)
            Log.ERROR -> Log.e(tag, message)
            Log.VERBOSE -> Log.v(tag, message)
        }
    }

    /**
     * 根据type输出日志消息，包括方法名，方法行数，Message，Throwable异常消息
     *
     * @param type
     * @param message
     */
    private fun log(type: Int, message: String, tr: Throwable) {
        var message = message
        val stackTrace = Thread.currentThread().stackTrace[4]
        val className = stackTrace.className
        val tag = className.substring(className.lastIndexOf('.') + 1)
        message = (stackTrace.methodName + "#" + stackTrace.lineNumber + " [" + message
                + "]")
        when (type) {
            Log.DEBUG -> Log.d(tag, message, tr)
            Log.INFO -> Log.i(tag, message, tr)
            Log.WARN -> Log.w(tag, message, tr)
            Log.ERROR -> Log.e(tag, message, tr)
            Log.VERBOSE -> Log.v(tag, message, tr)
        }
    }

    fun d(tag: String, message: String) {
        if (DEBUG)
            Log.d(tag, message)
    }

    fun i(tag: String, message: String) {
        if (DEBUG)
            Log.i(tag, message)
    }

    fun w(tag: String, message: String) {
        if (DEBUG)
            Log.w(tag, message)
    }

    fun e(tag: String, message: String) {
        if (DEBUG)
            Log.e(tag, message)
    }

    fun v(tag: String, message: String) {
        if (DEBUG)
            Log.v(tag, message)
    }

    fun d(message: String) {
        if (DEBUG)
            log(Log.DEBUG, message)
    }

    fun i(message: String) {
        if (DEBUG)
            log(Log.INFO, message)
    }

    fun w(message: String) {
        if (DEBUG)
            log(Log.WARN, message)
    }

    fun e(message: String) {
        if (DEBUG)
            log(Log.ERROR, message)
    }

    fun v(message: String) {
        if (DEBUG)
            log(Log.VERBOSE, message)
    }

    fun d(message: String, tr: Throwable) {
        if (DEBUG)
            log(Log.DEBUG, message, tr)
    }

    fun i(message: String, tr: Throwable) {
        if (DEBUG)
            log(Log.INFO, message, tr)
    }

    fun w(message: String, tr: Throwable) {
        if (DEBUG)
            log(Log.WARN, message, tr)
    }

    fun e(message: String, tr: Throwable) {
        if (DEBUG)
            log(Log.ERROR, message, tr)
    }

    fun v(message: String, tr: Throwable) {
        if (DEBUG)
            log(Log.VERBOSE, message, tr)
    }
}
