package com.wang.mytest.ui.view.surfaceview

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class SurfaceViewActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MySurfaceView(this))
    }
}