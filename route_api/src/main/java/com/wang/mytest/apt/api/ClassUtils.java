package com.wang.mytest.apt.api;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import dalvik.system.DexFile;

public class ClassUtils {

    private static final String TAG = "ClassUtils";

    public static Set<String> getFileNameByPackageName(Context context, String packageName) throws PackageManager.NameNotFoundException, InterruptedException {
        Set<String> classNames = new HashSet<>();
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        List<String> paths = new ArrayList<>();
        paths.add(applicationInfo.sourceDir);

        CountDownLatch parserCtl = new CountDownLatch(paths.size());

        for (final String path : paths) {
            Executors.newSingleThreadExecutor().execute(() -> {
                DexFile dexfile = null;
                try {
                    if (path.endsWith(".zip")) {
                        // NOT use new DexFile(path), because it will throw "permission error in /data/dalvik-cache"
                        dexfile = DexFile.loadDex(path, path + ".tmp", 0);
                    } else {
                        dexfile = new DexFile(path);
                    }

                    Enumeration<String> dexEntries = dexfile.entries();
                    while (dexEntries.hasMoreElements()) {
                        String className = dexEntries.nextElement();
                        if (className.startsWith(packageName)) {
                            classNames.add(className);
                        }
                    }
                } catch (Throwable ignore) {
                } finally {
                    if (dexfile != null) {
                        try {
                            dexfile.close();
                        } catch (Throwable ignore) {
                        }
                    }
                    parserCtl.countDown();
                }
            });
        }
        parserCtl.await();
        return classNames;
    }
}