package com.wang.soundrecorder

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }
}