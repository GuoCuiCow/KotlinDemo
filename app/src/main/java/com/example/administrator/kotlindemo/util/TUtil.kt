package com.example.administrator.kotlindemo.util

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * desc: Presenter获取
 * author：rookie on 16/12/6 下午4:55
 */
object TUtil {

    fun <T> getT(o: Any, i: Int): T? {
        try {
            val type = o.javaClass.genericSuperclass ?: return null
            if (type is ParameterizedType) {
                return (type.actualTypeArguments[i] as Class<T>)
                        .newInstance()
            }
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return null
    }

    fun forName(className: String): Class<*>? {
        try {
            return Class.forName(className)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return null
    }
}