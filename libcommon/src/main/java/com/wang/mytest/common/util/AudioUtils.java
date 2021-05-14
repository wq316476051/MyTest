package com.wang.mytest.common.util;

import android.media.AudioManager;

import com.wang.mytest.common.util.AppUtils;

import androidx.core.content.ContextCompat;

public class AudioUtils {

    private AudioUtils() {}

    public static boolean isRecording() {
        AudioManager audioManager = ContextCompat.getSystemService(AppUtils.getApp(), AudioManager.class);
        return !audioManager.getActiveRecordingConfigurations().isEmpty();
    }
}
