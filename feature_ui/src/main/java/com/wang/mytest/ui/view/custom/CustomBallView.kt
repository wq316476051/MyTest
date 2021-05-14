package com.wang.mytest.ui.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomBallView : View {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
    }
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx: Float = (left + right).toFloat() / 2
        val cy: Float = (top + bottom).toFloat() / 2
        val radius: Float = width.coerceAtMost(height).toFloat() / 2
        canvas.drawCircle(cx, cy, radius, paint)
    }
}