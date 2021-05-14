package com.wang.mytest.ui.view.custom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wang.mytest.apt.annotation.Route
import com.wang.mytest.R

@Route(path = "/activity/ui/view/custom", title = "CustomView")
class CustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)


    }
}