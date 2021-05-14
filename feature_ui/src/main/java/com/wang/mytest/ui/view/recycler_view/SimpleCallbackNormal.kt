package com.wang.mytest.ui.view.recycler_view

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * 1、左滑删除、右滑删除
 * 2、长按拖拽，调整位置
 */
class SimpleCallbackNormal(private val myAdapter: AdapterNormal) : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN
                or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ItemTouchHelper.START or ItemTouchHelper.END) {

    companion object {
        private const val TAG = "SimpleCallbackNormal"
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val fromPosition = viewHolder.adapterPosition; // 当前ViewHolder的position
        val toPosition = target.adapterPosition; // 目标ViewHolder的position

        Log.d(TAG, "onMove: from $fromPosition to $toPosition");

        //交换位置
        if (fromPosition < toPosition) {
            for (i in fromPosition..toPosition) {
                if (i + 1 < myAdapter.data!!.size) {
                    Collections.swap(myAdapter.data, i, i + 1)
                }
            }
        } else {
            for (i in fromPosition downTo toPosition) {
                if (i >= 1) {
                    Collections.swap(myAdapter.data, i, i - 1)
                }
            }
        }
        myAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition;
        myAdapter.data?.removeAt(position); //删除数据
        myAdapter.notifyItemRemoved(position);
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //滑动时改变Item的透明度
            val alpha = 1 - Math.abs(dX) / viewHolder.itemView.width;
            viewHolder.itemView.alpha = alpha;
            viewHolder.itemView.translationX = dX;
        }
    }
}