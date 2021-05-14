package com.wang.mytest;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Process;

import com.wang.mytest.apt.api.Router;
import com.wang.mytest.common.util.AppUtils;
import com.wang.mytest.common.util.LogUtils;

public class App extends Application {

    private static final String TAG = "App";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppUtils.init(this);
        Router.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(mLocaleChangeReceiver, new IntentFilter(Intent.ACTION_LOCALE_CHANGED));
    }

    private BroadcastReceiver mLocaleChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.d(TAG, "onReceive: " + intent.getAction());
            new Handler().postDelayed(() -> {
                unregisterReceiver(this);
                Process.killProcess(Process.myPid());
            }, 50);
        }
    };
}
