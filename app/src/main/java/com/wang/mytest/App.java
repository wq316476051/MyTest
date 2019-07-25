package com.wang.mytest;

import android.app.Application;
import android.util.Log;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.apt.api.Router;

public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        Router.init(this);
    }
}
