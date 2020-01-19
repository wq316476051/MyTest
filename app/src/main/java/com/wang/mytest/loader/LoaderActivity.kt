package com.wang.mytest.loader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.Loader
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