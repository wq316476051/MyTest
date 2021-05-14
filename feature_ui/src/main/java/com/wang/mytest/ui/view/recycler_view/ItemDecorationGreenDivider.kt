package com.wang.mytest.ui.view.recycler_view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Divider
 */
class ItemDecorationGreenDivider : RecyclerView.ItemDecoration() {

    companion object {
        private const val TAG = "ItemDecorationGreenDivider"
        private const val DIVIDER_HEIGHT = 10
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
        color = Color.GREEN
    }
    private var dividerHeight: Int = 0

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) >= 0) {
            outRect.top = DIVIDER_HEIGHT
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount: Int = parent.childCount
        for (i in 0..childCount) {
            val view = parent.getChildAt(i)
            if (parent.getChildAdapterPosition(view) >= 0) {
                val top: Float = (view.top - DIVIDER_HEIGHT).toFloat()
                val left: Float = (parent.paddingLeft + view.paddingLeft).toFloat()
                val right: Float = (parent.width - parent.paddingRight - view.paddingRight).toFloat()
                val bottom: Float = view.top.toFloat()
                c.drawRect(left,top,right,bottom,paint);
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }
}