package com.wang.mytest.common.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;

public final class AppUtils {

    private static Application sApp;

    public static void init(@NonNull Application app) {
        sApp = app;
    }

    @NonNull
    public static Application getApp() {
        if (sApp == null) {
            throw new RuntimeException("Init in Application first");
        }
        return sApp;
    }

    public static ActivityManager getActivityManager() {
        return getApp().getSystemService(ActivityManager.class);
    }

    public static PackageManager getPackageManager() {
        return getApp().getPackageManager();
    }

    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public static boolean isAtLeastP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    public static boolean isAtMostIceCream() {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    public static boolean isAtLeastJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }
}
