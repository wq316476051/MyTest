package com.wang.mytest.feature.ui.view.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.wang.mytest.library.common.LogUtils

class CustomView : View {

    companion object {
        private const val TAG = "CustomView"
        private const val FIRST_POINTER_ID = 0
    }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return false
        }
        /*
         1、第一根手指按下 pointerId=0
         2、第二根手指按下 pointerId=1
         3、第一根手指抬起 pointerId=0 没了
         4、第三根手指按下 pointerId=0 ***
         */
        if (!isFirstPointer(event)/* 第一个触点 */) {
            return super.onTouchEvent(event)
        }
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                handleActionDown(event)
            }
            MotionEvent.ACTION_MOVE -> {
                handleActionMove(event)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                handleActionUp(event)
            }
        }
        return true
    }

    private fun handleActionDown(event: MotionEvent) {
        if (isFirstPointer(event)) {
            val rawX = event.getRawX(event.actionIndex)
            val rawY = event.getRawY(event.actionIndex)
            LogUtils.d(TAG, "handleActionMove: rawX = $rawX, rawY = $rawY")
        }
    }

    /**
     * MotionEvent#getActionIndex() 仅对 ACTION_DOWN 和 ACTION_UP
     * MotionEvent#getActionIndex() 对 ACTION_MOVE 没有影响
     */
    private fun handleActionMove(event: MotionEvent) {
        val pointerIndex = event.findPointerIndex(FIRST_POINTER_ID)
        if (pointerIndex < 0 || pointerIndex >= event.pointerCount) {
            return
        }
        val rawX = event.getRawX(pointerIndex)
        val rawY = event.getRawY(pointerIndex)
        LogUtils.d(TAG, "handleActionMove: rawX = $rawX, rawY = $rawY")
    }

    private fun handleActionUp(event: MotionEvent) {
        if (isFirstPointer(event)) {
            val rawX = event.getRawX(event.actionIndex)
            val rawY = event.getRawY(event.actionIndex)
            LogUtils.d(TAG, "handleActionMove: rawX = $rawX, rawY = $rawY")
        }
    }

    private fun isFirstPointer(event: MotionEvent): Boolean {
        return event.getPointerId(event.actionIndex) == FIRST_POINTER_ID
    }
}