package com.wang.mytest.feature.storage.database

import android.os.Bundle

import com.wang.mytest.apt.annotation.Route
import com.wang.mytest.feature.storage.R
import androidx.fragment.app.FragmentActivity
import com.wang.mytest.feature.storage.database.table.Audio

@Route(path = "/activity/storage/database", title = "Database")
class DatabaseActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        val myLoader = MyLoader(this)
        myLoader.registerListener(1) { _, data ->
            if (data == null) {
                return@registerListener
            }
            data.apply {
                if (moveToFirst()) {
                    val filePath = getString(getColumnIndex(Audio.FILE_PATH))
                    val fileName = getString(getColumnIndex(Audio.FILE_NAME))
                }
            }
        }

        myLoader.registerOnLoadCanceledListener {

        }

        myLoader.startLoading()
    }

    private fun insert() {
    }

    companion object {
        private const val TAG = "DatabaseActivity"
    }
}
