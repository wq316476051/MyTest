package com.wang.mytest.ui.view.view_pager2;

import android.os.Bundle;
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2
import com.wang.mytest.R

class ViewPager2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2)

        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager2.adapter = AdapterNormal().apply {
            list = listOf(1, 2, 3, 4)
        }
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                if (position == 1) {
//                    viewPager2.isUserInputEnabled = false
//                }
                Toast.makeText(this@ViewPager2Activity,
                        "page selected $position", Toast.LENGTH_SHORT).show()
            }
        })

        findViewById<Button>(R.id.btn_fake_drag).setOnClickListener {
            viewPager2.beginFakeDrag()
            if (viewPager2.fakeDragBy(-310f))
                viewPager2.endFakeDrag()
        }
    }
}
