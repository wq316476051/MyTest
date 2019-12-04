package com.wang.mytest.apt.api;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.wang.mytest.apt.annotation.RouteBean;
import com.wang.mytest.apt.api.utils.ClassUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RouteStore {
    private static final String TAG = "RouteStore";

    private static final Map<String, RouteBean> mMap = new HashMap<>();

    public static void init(Application application) {
        Log.d(TAG, "init: ");
        try {
            Set<String> classes = ClassUtils.getFileNameByPackageName(application, application.getPackageName());
            classes.stream().filter(sss -> sss.contains("RecordListActivity")).forEach(sss -> {
                Log.d(TAG, "init: sss = " + sss);
            });
            Log.d(TAG, "init: classes = " + classes);
            for (String className : classes) {
                Class<?> name = Class.forName(className);

                if (!IRouteProvider.class.equals(name) && IRouteProvider.class.isAssignableFrom(name)) {

                    IRouteProvider routeProvider = (IRouteProvider) name.newInstance();
                    Map<String, RouteBean> map = routeProvider.get();
                    Log.d(TAG, "init: map = " + map);
                    mMap.putAll(map);
                }
            }
        } catch (InterruptedException | PackageManager.NameNotFoundException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static RouteBean get(String path) {
        return mMap.get(path);
    }

    public static List<RouteBean> getAll() {
        return new ArrayList<>(mMap.values());
    }
}
