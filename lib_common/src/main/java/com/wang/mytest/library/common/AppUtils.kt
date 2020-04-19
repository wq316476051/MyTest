package com.wang.mytest.library.common

import android.app.Application
import android.os.Build

object AppUtils {

    lateinit var app: Application
        private set

    val isAtLeastO: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    fun init(app: Application) {
        this.app = app
    }
}
