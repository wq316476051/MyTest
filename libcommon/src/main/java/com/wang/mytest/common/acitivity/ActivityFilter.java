package com.wang.mytest.common.acitivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;

import com.wang.mytest.common.util.LogUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityFilter {

    private static final String TAG = "ActivityFilter";

    private static final String ACTION = "test.TEST";
    private static final String META_NAME = "test.TEST";
    private static final String META_SUMMARY = "test.SUMMARY";

    private static TestBean rootBean = new TestBean();

    private static final List<TestBean> TEST_BEANS = new LinkedList<>();

    public static List<TestBean> getActivities(LinkedList<String> segment) {
        final StringBuilder prefix = new StringBuilder();
        for (String s : segment) {
            prefix.append("/").append(s);
        }

        final List<TestBean> result = new ArrayList<>();
        for (TestBean testBean : TEST_BEANS) {
            if (testBean.match(prefix.toString())) {
                result.add(testBean);
            }
        }
        return result;
    }

    public static void init(PackageManager packageManager) {
        LogUtils.d(TAG, "init: packageManager = " + packageManager);
        Intent intent = new Intent(ACTION);
        List<ResolveInfo> resolveInfoList
                = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo resolveInfo : resolveInfoList) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            LogUtils.d(TAG, "init: packageName = " + activityInfo.packageName + "; name = " + activityInfo.name);

            if (TextUtils.isEmpty(activityInfo.packageName) || TextUtils.isEmpty(activityInfo.name)) {
                continue;
            }
            ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.name);

            Bundle metaData = activityInfo.metaData;
            if (metaData == null) {
                continue;
            }
            String summary = metaData.getString(META_SUMMARY);
            String path = metaData.getString(META_NAME);
            if (TextUtils.isEmpty(summary) || TextUtils.isEmpty(path)) {
                continue;
            }
            TEST_BEANS.add(TestBean.createFolder(summary, path, componentName));
        }

        rootBean.setPath("/activity");
        rootBean.setNext(TEST_BEANS);
    }


}
