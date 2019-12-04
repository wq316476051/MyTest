package com.wang.mytest

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test_kotlin.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class TestKotlinActivity : AppCompatActivity() {

    val isHeadset: Boolean by DelegateAAA()

    var property by Delegates.observable("") {
        d, old, new ->
        d.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_kotlin)

        toast("")
        longToast("")

        tv_title.text = ""

        doAsync {
            uiThread { longToast("") }
        }
    }

    inner class DelegateAAA : ReadWriteProperty<Any?, Boolean> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
            return getSharedPreferences("name", Context.MODE_PRIVATE).getBoolean("key", false)
        }

        @SuppressWarnings
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
            getSharedPreferences("name", Context.MODE_PRIVATE).edit().putBoolean("key", value).apply()
        }
    }
}

