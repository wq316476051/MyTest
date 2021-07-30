package com.mytest.app_launcher.recent;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.Context;

import java.util.List;

/**
 * 主动获取
 * 被动监听
 */
public class RecentModel {

    public List<TaskInfo> getTaskInfo(Context context) {
        ActivityManager activityManager = context.getSystemService(ActivityManager.class);
        List<ActivityManager.RecentTaskInfo> recentTasks
                = activityManager.getRecentTasks(20, ActivityManager.RECENT_IGNORE_UNAVAILABLE);


        return null;
    }
}
