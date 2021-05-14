package com.wang.mytest.ui.view.recycler_view

import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.R

/**
 * Banner flag
 */
class ItemDecorationFlag : RecyclerView.ItemDecoration() {

    companion object {
        private const val TAG = "ItemDecorationFlag"
    }

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 10
        outRect.left = view.context.resources.getDimensionPixelOffset(R.dimen.default_margin_start) * 3
        outRect.right = view.context.resources.getDimensionPixelOffset(R.dimen.default_margin_start)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        for (i in 0..parent.childCount) {
            c.drawColor(Color.WHITE)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        for (i in 0..parent.childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            when {
                position < 0 -> return  // 有可能小于 0，此时 child view 可能为 null
                position == 0 -> {
                    val defaultMargin = parent.context.resources.getDimensionPixelOffset(R.dimen.default_margin_start)
                    val bitmap = BitmapFactory.decodeResource(parent.context.resources, android.R.drawable.ic_delete, BitmapFactory.Options().apply {
                        inMutable = true
                    }).apply { eraseColor(Color.parseColor("#FF0000")) }
                    val left = defaultMargin.toFloat()/2
                    val top = (view.top + view.bottom).toFloat()/2 - bitmap.height.toFloat() / 2
                    c.drawBitmap(bitmap, left, top, paint)
                }
                position == 1 -> {
                    val defaultMargin = parent.context.resources.getDimensionPixelOffset(R.dimen.default_margin_start)
                    val bitmap = BitmapFactory.decodeResource(parent.context.resources, android.R.drawable.ic_delete, BitmapFactory.Options().apply {
                        inMutable = true
                    }).apply { eraseColor(Color.parseColor("#AAFF0000")) }
                    val left = defaultMargin.toFloat()/2
                    val top = (view.top + view.bottom).toFloat()/2 - bitmap.height.toFloat() / 2
                    c.drawBitmap(bitmap, left, top, paint)
                }
                position == 2 -> {
                    val defaultMargin = parent.context.resources.getDimensionPixelOffset(R.dimen.default_margin_start)
                    val bitmap = BitmapFactory.decodeResource(parent.context.resources, android.R.drawable.ic_delete, BitmapFactory.Options().apply {
                        inMutable = true
                    }).apply { eraseColor(Color.parseColor("#33FF0000")) }
                    val left = defaultMargin.toFloat()/2
                    val top = (view.top + view.bottom).toFloat()/2 - bitmap.height.toFloat() / 2
                    c.drawBitmap(bitmap, left, top, paint)
                }
                else -> {
                    val defaultMargin = parent.context.resources.getDimensionPixelOffset(R.dimen.default_margin_start)
                    val bitmap = BitmapFactory.decodeResource(parent.context.resources, android.R.drawable.ic_delete, BitmapFactory.Options().apply {
                        inMutable = true
                    }).apply { eraseColor(Color.parseColor("#33000000")) }
                    val left = defaultMargin.toFloat()/2
                    val top = (view.top + view.bottom).toFloat()/2/*中间位置*/ - bitmap.height.toFloat() / 2/*半个Bitmap高度*/
                    c.drawBitmap(bitmap, left, top, paint)
                }
            }
        }
    }
}