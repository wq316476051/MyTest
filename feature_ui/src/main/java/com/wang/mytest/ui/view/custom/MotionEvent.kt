package com.wang.mytest.ui.view.custom

import android.view.MotionEvent

fun getActionString(action: Int): String {
    return when (action) {
        MotionEvent.ACTION_DOWN -> "ACTION_DOWN"
        MotionEvent.ACTION_MOVE -> "ACTION_MOVE"
        MotionEvent.ACTION_UP -> "ACTION_UP"
        MotionEvent.ACTION_CANCEL -> "ACTION_CANCEL"
        MotionEvent.ACTION_POINTER_DOWN -> "ACTION_POINTER_DOWN"
        MotionEvent.ACTION_POINTER_UP -> "ACTION_POINTER_UP"
        else -> action.toString()
    }
}