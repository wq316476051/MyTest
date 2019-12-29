package com.wang.mytest.feature.ui.recycler_view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.feature.ui.R

/**
 * 1、header & content & footer
 * 2、click & delete
 */
class AdapterNormal : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "AdapterNormal"
        private const val TYPE_HEADER = 1
        private const val TYPE_CONTENT = 2
        private const val TYPE_FOOTER = 3
    }

    var itemClickListener: ((View, Int) -> Unit)? = null
    var itemDeleteListener: ((View, Int) -> Unit)? = null
    var data: MutableList<String>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var headerCount = 1
        private set
    var footerCount = 1
        private set

    override fun getItemCount(): Int = headerCount + footerCount + (data?.size ?: 0)

    override fun getItemViewType(position: Int): Int {
        return when {
            position < headerCount -> TYPE_HEADER
            position >= headerCount + (data?.size ?: 0) -> TYPE_FOOTER
            else -> TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> ViewHolderHeader(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view_header, parent, false))
            TYPE_FOOTER -> ViewHolderFooter(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view_footer, parent, false))
            else -> ViewHolderContent(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderHeader -> {
                holder.tvTitle.text = "Header"
            }
            is ViewHolderFooter -> {
                holder.tvTitle.text = "Footer"
            }
            is ViewHolderContent -> {
                data?.get(holder.layoutPosition - headerCount)?.also {
                    holder.tvTitle.text = it

                    holder.itemView.setOnClickListener {
                        itemClickListener?.invoke(holder.itemView, holder.layoutPosition - headerCount)
                    }
                    holder.ivDelete.setOnClickListener {
                        data?.removeAt(holder.layoutPosition - headerCount)?.also {
                            notifyItemRemoved(holder.layoutPosition)
                            itemDeleteListener?.invoke(holder.ivDelete, holder.layoutPosition - headerCount)
                        }
                    }
                }
            }
            else -> Log.e(TAG, "onBindViewHolder: unknown holder.")
        }
    }
}