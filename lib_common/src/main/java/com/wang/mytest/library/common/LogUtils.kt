package com.wang.mytest.library.common

import android.util.Log

object LogUtils {

    private const val PREFIX = "MyTest"

    fun d(tag: String, msg: String) {
        Log.d("$PREFIX: $tag", msg)
    }
}