package com.wang.mytest.utils;

import android.media.AudioManager;

import com.wang.mytest.library.common.AppUtils;

import androidx.core.content.ContextCompat;

public class AudioUtils {

    private AudioUtils() {}

    public static boolean isRecording() {
        AudioManager audioManager = ContextCompat.getSystemService(AppUtils.INSTANCE.getApp(), AudioManager.class);
        return !audioManager.getActiveRecordingConfigurations().isEmpty();
    }
}
