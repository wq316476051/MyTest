package com.wang.mytest.ui.view.surface_view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.wang.mytest.apt.annotation.Route

@Route(path = "/activity/ui/surface_view", title = "SurfaceView")
class SurfaceViewActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MySurfaceView(this))
    }
}