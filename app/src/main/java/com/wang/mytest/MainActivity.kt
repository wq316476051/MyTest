package com.wang.mytest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.apt.annotation.RouteBean
import com.wang.mytest.apt.api.RouteStore
import com.wang.mytest.feature.ui.ScreenActivity
import com.wang.mytest.feature.ui.view_pager2.ViewPager2Activity
import com.wang.mytest.lifecycle.LifecycleActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_1.view.*
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class MainActivity : AppCompatActivity() {

    private val mClickable = AtomicBoolean(true)

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = object : FrameLayout(this) {
            override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
                return if (!mClickable.get()) {
                    true
                } else super.onInterceptTouchEvent(ev)
            }
        }

        layoutInflater.inflate(R.layout.activity_main, rootView, true)
        setContentView(rootView)
        Log.d(TAG, "onCreate: ");

        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.itemAnimator = DefaultItemAnimator()
        Log.d(TAG, "onCreate: " + RouteStore.getAll());
        recycler_view.adapter = NormalAdapter(RouteStore.getAll())

        btn_test.setOnClickListener {
            startActivity(Intent().setClass(this, LifecycleActivity::class.java))
        }

        lifecycleScope.launch {

        }

        lifecycleScope.launchWhenCreated {

        }

        lifecycleScope.launchWhenStarted {

        }

        lifecycleScope.launchWhenResumed {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.clickable, it)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.options_clickable -> {
                mClickable.compareAndSet(false, true)
                true
            }
            R.id.options_unclickable -> {
                mClickable.compareAndSet(true, false)
                true
            }
            R.id.options_screen_details -> {
                startActivity(Intent(this, ScreenActivity::class.java))
                true
            }
            R.id.options_viewpager2 -> {
                startActivity(Intent(this, ViewPager2Activity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }

    inner class NormalAdapter(private val mDataList: List<RouteBean>) : RecyclerView.Adapter<NormalAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun getItemCount(): Int {
            return mDataList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_1, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.title.text  = mDataList[position].title
            holder.itemView.setOnClickListener { _ ->
                val routeBean = mDataList[position]
                val intent = Intent().setClassName(this@MainActivity, routeBean.className)
                startActivity(intent)
            }
        }
    }
}