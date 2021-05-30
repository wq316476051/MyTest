package com.wang.mytest.ui.view.viewpager2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.ui.R

class AdapterNormal : RecyclerView.Adapter<ViewHolderNormal>() {

    companion object {
        private val colors = arrayOf("#CCFF99", "#41F1E5", "#8D41F1", "#FF99CC")
    }
    var list: List<Int> = ArrayList()

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNormal {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager2, parent, false)
        return ViewHolderNormal(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderNormal, position: Int) {
        holder.tvText.text = list[position].toString()
        holder.tvText.setBackgroundColor(Color.parseColor(colors[position % colors.size]))
    }
}