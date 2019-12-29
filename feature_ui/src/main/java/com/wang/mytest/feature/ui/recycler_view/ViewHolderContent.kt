package com.wang.mytest.feature.ui.recycler_view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.feature.ui.R

class ViewHolderContent(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    val ivDelete: ImageView = itemView.findViewById(R.id.iv_delete)
}