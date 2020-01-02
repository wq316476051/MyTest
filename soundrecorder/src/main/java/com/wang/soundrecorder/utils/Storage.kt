package com.wang.soundrecorder.utils

import android.os.Environment
import java.io.File

object Storage {

    fun getRecordingsDir(): File {
        return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "recordings").apply {
            if (!exists()) {
                mkdirs()
            }
        }
    }
}