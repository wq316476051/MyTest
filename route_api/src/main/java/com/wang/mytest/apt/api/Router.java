package com.wang.mytest.apt.api;

import android.app.Application;
import android.util.Log;

public class Router {

    private static final String TAG = "Router";

    public static void init(Application application) {
        Log.d(TAG, "init: ");
        RouteStore.init(application);
    }
}
