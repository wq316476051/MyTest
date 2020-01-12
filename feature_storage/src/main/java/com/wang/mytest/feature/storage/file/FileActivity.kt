package com.wang.mytest.feature.storage.file

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wang.mytest.library.common.hasStoragePermission
import com.wang.mytest.library.common.requestStoragePermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FileActivity : AppCompatActivity() {

    companion object {
        const val TAG = "FileActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (hasStoragePermission(this)) {
            onStoragePermissionGranted();
        } else {
            requestStoragePermission(this, 1024)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1024 && hasStoragePermission(this)) {
            onStoragePermissionGranted();
        }
    }

    private fun onStoragePermissionGranted() {
        GlobalScope.launch {
            Log.d(TAG, "start: threadName = ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                val files = Environment.getExternalStorageDirectory().listFiles()
                Log.d(TAG, "in: threadName = ${Thread.currentThread().name}")
                for (file in files) {
                    Log.d(TAG, "file.length = ${file.length()}")
                }
            }
            Log.d(TAG, "end: threadName = ${Thread.currentThread().name}")
        }
    }
}