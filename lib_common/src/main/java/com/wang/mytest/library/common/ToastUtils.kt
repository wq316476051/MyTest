package com.wang.mytest.library.common

import android.widget.Toast

object ToastUtils {

    fun showShort(text: String) {
        Toast.makeText(AppUtils.app, text, Toast.LENGTH_SHORT).show()
    }
}
