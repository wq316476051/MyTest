package com.wang.mytest

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import junit.framework.TestSuite
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyAndroidTest : TestCase() {

    override fun setUp() {
        super.setUp()
        Log.d(TAG, "setUp: ")
        println("setUp")
    }

    fun test() {
        Log.d(TAG, "test: ")
        println("test")
    }

    override fun tearDown() {
        super.tearDown()
        Log.d(TAG, "tearDown: ")
        println("tearDown")
    }

    companion object {
        private const val TAG = "MyAndroidTest"
    }
}