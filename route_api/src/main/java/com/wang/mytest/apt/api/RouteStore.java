package com.wang.mytest.apt.api;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RouteStore {

    private static final String TAG = "RouteStore";

    private static final Map<String, String> mMap = new HashMap<>();

    public static void init(Application application) {
        Log.d(TAG, "init: ");
        try {
            Set<String> classes = ClassUtils.getFileNameByPackageName(application, application.getPackageName());
            Log.d(TAG, "init: classes = " + classes);
            for (String className : classes) {
                Class<?> name = Class.forName(className);

                Log.d(TAG, "init: name = " + name);

                if (!IRouteProvider.class.equals(name) && IRouteProvider.class.isAssignableFrom(name)) {

                    IRouteProvider routeProvider = (IRouteProvider) name.newInstance();
                    Map<String, String> map = routeProvider.get();
                    Log.d(TAG, "init: map = " + map);
                    mMap.putAll(map);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void register(Map<String, String> map) {
        mMap.putAll(map);
    }

    public static String get(String path) {
        return mMap.get(path);
    }

    public static Set<String> getAll() {
        return mMap.keySet();
    }
}
