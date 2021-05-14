package com.wang.mytest.clean

import com.wang.mytest.common.util.AppUtils
import java.io.File

object Cleaner {

    private const val DATABASE = 0
    private const val INTERNAL_FILE = 1
    private const val INTERNAL_CACHE = 2
    private const val EXTERNAL_PRIVATE_FILE = 3
    private const val EXTERNAL_PUBLIC_FILE = 4

    fun cleanAll() {
        clean(DATABASE)
        clean(INTERNAL_FILE)
        clean(INTERNAL_CACHE)
        clean(EXTERNAL_PRIVATE_FILE)
        clean(EXTERNAL_PUBLIC_FILE)
    }

    fun clean(type: Int) {
        when (type) {
            DATABASE -> {
            }
            INTERNAL_FILE -> {
            }
            INTERNAL_CACHE -> {
            }
            EXTERNAL_PRIVATE_FILE -> {
            }
            EXTERNAL_PUBLIC_FILE -> {
                deleteFile(AppUtils.getApp().filesDir, FileMatchers.audioFile())
                deleteFile(AppUtils.getApp().filesDir) { fileName -> fileName.endsWith(".temp")}
                deleteFile(AppUtils.getApp().filesDir, FileMatchers.allOf(FileMatchers.audioFile()))
            }
        }
    }

    private fun deleteFile(dir: File?, matcher: FileMatcher<String>) {
        dir?.apply {
            dir.listFiles().forEach { file ->
                if (matcher.matches(file.name)) {
                    file.delete()
                }
            }
        }
    }

    private fun deleteFile(dir: File?, matcher: (String) -> Boolean) {
        dir?.apply {
            dir.listFiles().forEach { file ->
                if (matcher.invoke(file.name)) {
                    file.delete()
                }
            }
        }
    }
}