package com.wang.mytest.ui.view.recycler_view

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.wang.mytest.apt.annotation.Route
import com.wang.mytest.R
import com.wang.mytest.common.toast

/**
 * 参考：
 * https://blog.csdn.net/wangcheng_/article/details/80753216
 */
@Route(path = "/activity/ui/recycler_view", title = "RecyclerView")
class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: AdapterNormal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(ItemDecorationFlag())
        recyclerView.adapter = AdapterNormal().apply {
            myAdapter = this
            data = MutableList(50) { i -> "item-$i" }
            itemClickListener =  { _, position ->
                toast("click position = $position")
            }
            itemDeleteListener = { _, position ->
                toast("delete position = $position")
            }
        }

        ItemTouchHelper(SimpleCallbackNormal(myAdapter)).attachToRecyclerView(recyclerView)
    }
}