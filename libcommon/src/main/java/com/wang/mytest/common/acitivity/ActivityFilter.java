package com.wang.mytest.common.acitivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;

import com.wang.mytest.common.Constants;
import com.wang.mytest.common.util.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityFilter {

    private static final String TAG = "ActivityFilter";

    public static List<Entity> getActivities() {
        Intent intent = new Intent(Constants.ACTION_MAIN);

        PackageManager packageManager = AppUtils.getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA);
        if (resolveInfoList.isEmpty()) {
            return Collections.emptyList();
        }

        final List<Entity> result = new ArrayList<>(resolveInfoList.size());
        for (ResolveInfo resolveInfo : resolveInfoList) {
            final ActivityInfo activityInfo = resolveInfo.activityInfo;
            String packageName = activityInfo.packageName;
            String className = activityInfo.name;

            Bundle metaData = activityInfo.metaData;
            String summary = metaData.getString(Constants.NAME_SUMMARY);

            if (TextUtils.isEmpty(packageName) || TextUtils.isEmpty(className) || TextUtils.isEmpty(summary)) {
                continue;
            }

            Entity entity = new Entity(packageName, className, summary);
            result.add(entity);
        }
        return result;
    }

    public static class Entity {

        private String mPackageName;

        private String mClassName;

        private String mSummary;

        public String getPackageName() {
            return mPackageName;
        }

        public void setPackageName(String packageName) {
            mPackageName = packageName;
        }

        public String getClassName() {
            return mClassName;
        }

        public void setClassName(String className) {
            mClassName = className;
        }

        public String getSummary() {
            return mSummary;
        }

        public void setSummary(String summary) {
            mSummary = summary;
        }

        public Entity(String packageName, String className, String summary) {
            mPackageName = packageName;
            mClassName = className;
            mSummary = summary;
        }
    }
}
