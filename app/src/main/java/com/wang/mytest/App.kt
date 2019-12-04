package com.wang.mytest

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.Process
import android.util.Log

import com.wang.mytest.apt.api.Router
import com.wang.mytest.library.common.AppUtils

class App : Application() {

    companion object {
        const val TAG = "App"
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        AppUtils.init(this)
        Router.init(this)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
        registerReceiver(mLocaleChangeReceiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))
    }

    private val mLocaleChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d(TAG, "onReceive: " + intent.action)
            Handler().postDelayed({ Process.killProcess(Process.myPid()) }, 50)
        }
    }
}