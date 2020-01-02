package com.wang.soundrecorder.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")

@SuppressLint("SimpleDateFormat")
fun formatDate(date: Long): String = simpleDateFormat.format(Date(date))

fun Boolean.ifTrue(block: () -> Unit) {
    if (this) {
        block.invoke()
    }
}

fun Boolean.ifFalse(block: () -> Unit) {
    if (!this) {
        block.invoke()
    }
}