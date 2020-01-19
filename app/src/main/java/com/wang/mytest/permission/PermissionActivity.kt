package com.wang.mytest.permission

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.wang.mytest.R
import com.wang.mytest.library.common.*
import java.lang.StringBuilder
import java.util.*

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        findViewById<Button>(R.id.btn_storage).apply {
            if (hasStoragePermission()) {
                toast("storage permission granted")
                // do something
            } else {
                requestStoragePermission()
            }
        }

        findViewById<Button>(R.id.btn_audio)
        findViewById<Button>(R.id.btn_both)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_STORAGE -> {
                // grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED
                if (hasStoragePermission()) {
                    // 用户点击“同意”
                    toast("storage permission 同意")
                    // do something
                } else {
                    if (shouldShowStorageRationale()) {
                        // 用户点击“禁止后不再提示”，并且以后申请此权限时不弹窗而是直接到这里
                        toast("storage permission 禁止后不再提示")
                        // 引导用户进入设置授权
                    } else {
                        // 用户点击“禁止”
                        toast("storage permission 禁止")
                    }
                }
            }
            REQUEST_CODE_AUDIO -> {
                if (hasAudioPermission()) {
                    // 用户点击“同意”
                    toast("audio permission 同意")
                    // do something
                } else {
                    if (shouldShowAudioRationale()) {
                        // 用户点击“禁止后不再提示”，并且以后申请此权限时不弹窗而是直接到这里
                        toast("audio permission 禁止后不再提示")
                        // 引导用户进入设置授权
                    } else {
                        // 用户点击“禁止”
                        toast("audio permission 禁止")
                    }
                }
            }
            REQUEST_CODE_STORAGE_AND_AUDIO -> {
                val hasStoragePermission = hasStoragePermission()
                val hasAudioPermission = hasAudioPermission()

                val shouldShowStorageRationale = shouldShowStorageRationale()
                val shouldShowAudioRationale = shouldShowAudioRationale()

                val dialog: AlertDialog
                val stringBuilder: StringBuilder = StringBuilder()
                if (!hasStoragePermission && !hasAudioPermission) {
                    if (shouldShowStorageRationale || shouldShowAudioRationale) {
                        stringBuilder.append("* storage")
                        stringBuilder.append("* audio")
                    }
                } else if (!hasStoragePermission && hasAudioPermission) {
                    if (shouldShowStorageRationale) {
                        stringBuilder.append("* storage")
                    }
                } else if (!hasStoragePermission && !hasAudioPermission) {
                    if (shouldShowAudioRationale) {
                        stringBuilder.append("* audio")
                    }
                } else {
                    toast("both permissions 同意")
                }
            }
        }
    }
}