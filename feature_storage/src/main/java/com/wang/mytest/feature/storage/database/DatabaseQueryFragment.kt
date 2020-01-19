package com.wang.mytest.feature.storage.database

import android.database.ContentObserver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.feature.storage.R
import com.wang.mytest.feature.storage.database.table.Label
import com.wang.mytest.library.common.logd

class DatabaseQueryFragment : Fragment() {

    companion object {
        private const val TAG = "DatabaseQueryFragment"
    }

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    private lateinit var myAdapter: MyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_database_query, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar_query)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MyAdapter().also { myAdapter = it }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
        context?.contentResolver?.registerContentObserver(LABEL_URI, true, object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                loadData()
            }
        })
    }

    private fun loadData() {
        logd(TAG, "loadData: ");
        context?.contentResolver?.query(LABEL_URI, null, null, null, null)?.use {
            logd(TAG, "loadData: count = ${it.count}");
            val data = mutableListOf<Data>()
            while (it.moveToNext()) {
                val filePath = it.getString(it.getColumnIndex(Label.FILE_PATH))
                val content = it.getString(it.getColumnIndex(Label.CONTENT))
                val time = it.getLong(it.getColumnIndex(Label.TIME))
                logd(TAG, "loadData: filePath = $filePath")
                logd(TAG, "loadData: content = $content")
                logd(TAG, "loadData: time = $time")
                if (filePath == null || content == null) {
                    continue
                }
                data.add(Data(filePath, content, time))
            }
            myAdapter.setData(data)
            myAdapter.notifyDataSetChanged()
        }
    }

    data class Data(var filePath: String, var content: String, var time: Long)

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFilePath: TextView = itemView.findViewById(R.id.tv_file_path)
        val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
    }

    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

        private var dataList: MutableList<Data> = mutableListOf()

        fun setData(list: List<Data>) {
            dataList.clear()
            dataList.addAll(list)
        }

        override fun getItemCount(): Int = dataList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_label, parent, false))
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data = dataList.get(position)
            holder.tvFilePath.text = data.filePath
            holder.tvContent.text = data.content
            holder.tvTime.text = data.time.toString()
        }
    }
}