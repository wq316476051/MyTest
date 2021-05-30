package com.wang.mytest.ui.view.viewpager2

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.ui.R

class ViewHolderNormal(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvText: TextView = itemView.findViewById(R.id.tv_text)
}