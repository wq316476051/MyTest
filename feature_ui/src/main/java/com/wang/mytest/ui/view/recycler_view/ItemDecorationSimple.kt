package com.wang.mytest.ui.view.recycler_view

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/*
parent.childCount // 不是获取RecyclerView 所有Item 的个数，而是当前屏幕可见的Item 个数
 */
class ItemDecorationSimple : RecyclerView.ItemDecoration() {

    /**
     * 用于撑开整个 itemView
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 10
        outRect.bottom = 10
    }

    /**
     * 用于绘制具体的分隔线形状，在 itemView 前面绘制，有可能被 itemView 覆盖
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    /**
     * 在 itemView 之后绘制，可以覆盖 itemView 的内容
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }
}