package com.wang.mytest.ui.column_pager

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ColumnPager : ViewGroup {

    companion object {
        val LAYOUT_ATTRS = intArrayOf(android.R.attr.layout_gravity)
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

    }

    interface Adapter {
        fun getItemCount(): Int
        fun getItem(): Fragment
    }

    fun setAdapter(adapter: Adapter) {

    }

    fun notifyDataSetChanged() {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var childWidthSize = measuredWidth - paddingLeft - paddingRight
        var childHeightSize = measuredHeight - paddingTop - paddingBottom
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val lp = child.layoutParams as LayoutParams
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams()
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams?): ViewGroup.LayoutParams {
        return generateDefaultLayoutParams()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams && super.checkLayoutParams(p)
    }

    class LayoutParams : ViewGroup.LayoutParams {

        var gravity: Int = Gravity.START

        constructor() : super(null, null)
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
            val typedArray = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS)
            gravity = typedArray.getInteger(0, Gravity.START)
            typedArray.recycle()
        }
    }
}