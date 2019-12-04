package com.wang.mytest.library.common;

import android.app.Application;
import android.os.Build;

import androidx.annotation.Nullable;

public class AppUtils {

    private static Application mApp;

    public static void init(Application app) {
        mApp = app;
    }

    public static Application getApp() {
        return mApp;
    }

    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }
}
