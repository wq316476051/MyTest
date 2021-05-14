package com.wang.mytest.ui.view.custom

import android.view.MotionEvent

fun MotionEvent.getRawX(pointerIndex: Int): Float {
    return getX(actionIndex) + rawX - x
}

fun MotionEvent.getRawY(pointerIndex: Int): Float {
    return getY(actionIndex) + rawY - y
}