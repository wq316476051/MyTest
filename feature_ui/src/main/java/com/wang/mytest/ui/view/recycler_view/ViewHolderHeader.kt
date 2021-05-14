package com.wang.mytest.ui.view.recycler_view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.R

class ViewHolderHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
}