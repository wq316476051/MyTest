package com.wang.mytest.feature.audio.utils;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class AudioUtils {

    private static final String TAG = "AudioUtils";

    private AudioUtils() {
    }

    public static long getDuration(File file) throws IOException {
        MediaExtractor extractor = new MediaExtractor();
        extractor.setDataSource(file.getAbsolutePath());
        int trackCount = extractor.getTrackCount();
        Log.d(TAG, "getDuration: trackCount = " + trackCount);
        for (int i = 0; i < trackCount; i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            Log.d(TAG, "getDuration: format = " + format);
            String mimeType = format.getString(MediaFormat.KEY_MIME);
            Log.d(TAG, "getDuration: mimeType = " + mimeType);
            if (mimeType != null && mimeType.startsWith("audio")) {
                return format.getLong(MediaFormat.KEY_DURATION) / 1000;
            }
        }
        return -1;
    }

    public static String formatSeconds(long seconds){
        String standardTime;
        if (seconds <= 0){
            standardTime = "00:00";
        } else if (seconds < 60) {
            standardTime = String.format(Locale.getDefault(), "00:%02d", seconds % 60);
        } else if (seconds < 3600) {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
        } else {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60);
        }
        return standardTime;
    }
}
