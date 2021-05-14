package com.wang.mytest.common

import android.app.Activity
import android.widget.Toast

fun Activity.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    runOnUiThread {
        Toast.makeText(this, text, duration).show()
    }
}