package com.wang.mytest;

import android.app.Application;
import android.content.Context;

import com.wang.mytest.common.observer.LocaleChangeObserver;
import com.wang.mytest.common.util.AppUtils;
import com.wang.mytest.common.util.LogUtils;

public class App extends Application {

    private static final String TAG = "App";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppUtils.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocaleChangeObserver.register(this, this::onLocaleChanged);
    }

    private void onLocaleChanged() {
        LogUtils.debug(TAG, "onLocaleChanged: ");
    }
}
