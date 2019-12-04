package com.wang.mytest.feature.audio.playback;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class NotificationHelper implements DefaultLifecycleObserver {

    public static NotificationHelper create(Context context) {
        return new NotificationHelper();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
    }
}
