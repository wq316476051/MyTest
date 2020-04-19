package com.wang.mytest.loader

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.viewpager.widget.ViewPager
import com.wang.mytest.R

class LoaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)

        val loader = SpeechLoader(this)
        loader.registerListener(11, Loader.OnLoadCompleteListener { loader, data ->
            data?.apply {
                moveToFirst()
                val value = getString(getColumnIndex("name"))
            }
        })
    }
}