package com.wang.mytest.common.util

import android.widget.Toast
import com.wang.mytest.common.util.AppUtils

object ToastUtils {

    fun showShort(text: String) {
        Toast.makeText(AppUtils.getApp(), text, Toast.LENGTH_SHORT).show()
    }
}
