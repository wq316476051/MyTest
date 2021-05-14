package com.wang.mytest.loader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.Loader
import com.wang.mytest.R

class LoaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)

        val loader = SpeechLoader(this).also {
            it.registerListener(11) { _, data ->
                data?.apply {
                    moveToFirst()
                    val value = getString(getColumnIndex("name"))
                }
            }
        }
    }
}