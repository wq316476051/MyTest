package com.wang.mytest.feature.storage.database

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wang.mytest.feature.storage.R

/**
 * BottomNavigationView + ViewPager2 + RecyclerView
 */
@Suppress("UNREACHABLE_CODE")
class DatabaseActivity : FragmentActivity() {

    companion object {
        private const val TAG = "DatabaseActivity"
        private const val ITEM_POSITION_QUERY = 0
        private const val ITEM_POSITION_INSERT = 1
        private const val ITEM_COUNT = 2
    }

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        viewPager = findViewById<ViewPager2>(R.id.view_pager).apply {
            adapter = MyAdapter(this@DatabaseActivity)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    Log.d(TAG, "onPageSelected: position = $position");
                }
            })
        }

        bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_previous -> {
                        viewPager.currentItem = ITEM_POSITION_QUERY
                    }
                    R.id.navigation_next -> {
                        viewPager.currentItem = ITEM_POSITION_INSERT
                    }
                }
                true
            }
        }
    }

    class MyAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = ITEM_COUNT

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                ITEM_POSITION_QUERY -> DatabaseQueryFragment()
                ITEM_POSITION_INSERT -> DatabaseInsertFragment()
                else -> Fragment()
            }
        }
    }
}
