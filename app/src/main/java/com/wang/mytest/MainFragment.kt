package com.wang.mytest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.apt.annotation.RouteBean
import com.wang.mytest.apt.api.RouteStore
import com.wang.mytest.feature.ui.ScreenActivity
import com.wang.mytest.feature.ui.view_pager2.ViewPager2Activity
import com.wang.mytest.lifecycle.LifecycleActivity
import java.util.concurrent.atomic.AtomicBoolean

class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBtnTest: Button

    private lateinit var mContext: Context
    private val mClickable = AtomicBoolean(true)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context.applicationContext
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: ")
        val rootView = object : FrameLayout(activity!!) {
            override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
                return if (!mClickable.get()) {
                    true
                } else super.onInterceptTouchEvent(ev)
            }
        }
        layoutInflater.inflate(R.layout.fragment_main, rootView, true)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        mBtnTest = view.findViewById(R.id.btn_test)
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRecyclerView.itemAnimator = DefaultItemAnimator()

        Log.i(TAG, "onViewCreated: " + RouteStore.getAll())
        mRecyclerView.adapter = NormalAdapter(RouteStore.getAll())

        mBtnTest.setOnClickListener {
            startActivity(Intent().setClass(context!!, LifecycleActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.clickable, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.options_clickable -> {
                mClickable.compareAndSet(false, true)
                true
            }
            R.id.options_unclickable -> {
                mClickable.compareAndSet(true, false)
                true
            }
            R.id.options_screen_details -> {
                startActivity(Intent(context, ScreenActivity::class.java))
                true
            }
            R.id.options_viewpager2 -> {
                startActivity(Intent(context, ViewPager2Activity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class NormalAdapter(private val mDataList: List<RouteBean>) : RecyclerView.Adapter<NormalAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            public val tvTitle: TextView = view.findViewById(R.id.tv_title)
        }

        override fun getItemCount(): Int {
            return mDataList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_1, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvTitle.text = mDataList[position].title
            holder.itemView.setOnClickListener {
                val routeBean = mDataList[position]
                val intent = Intent().setClassName(this@MainFragment.context!!, routeBean.className)
                startActivity(intent)
            }
        }
    }
}