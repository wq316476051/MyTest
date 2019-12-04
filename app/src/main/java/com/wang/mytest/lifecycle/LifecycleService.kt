package com.wang.mytest.lifecycle

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log

class LifecycleService : Service() {

    companion object {
        const val TAG = "LifecycleService"
    }

    private val mBinderService: BinderService by lazy { BinderService() };

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ");
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: ");
        // check permission
        return mBinderService;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ");
    }

    private class BinderService : ILifecycleService.Stub() {
        override fun call(method: String?, arge: String?, extras: Bundle?) {

        }
    }
}