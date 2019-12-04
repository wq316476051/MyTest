package com.wang.mytest.feature.audio;

import android.content.Context;

import java.io.File;
import java.util.Optional;

import androidx.annotation.NonNull;

public class RecordStorage {

    private static final String SUB_DIR = "recordings";

    private RecordStorage() {
    }

    @NonNull
    public static File getStorageDirectory(Context context) {
        return getExternalStorageDirectory(context).orElse(getInternalStorageDirectory(context));
    }

    @NonNull
    private static File getInternalStorageDirectory(Context context) {
        return new File(context.getFilesDir(), SUB_DIR);
    }

    private static Optional<File> getExternalStorageDirectory(Context context) {
        return Optional.ofNullable(context.getExternalFilesDir(SUB_DIR));
    }
}
