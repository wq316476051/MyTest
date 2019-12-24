package com.wang.mytest.coroutine

import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import kotlin.concurrent.thread

class TestActivity : ScopedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launch {
            val result1 = async(Dispatchers.IO) {
                getListFromFileSystem()
            }
            val result2 = async(Dispatchers.IO) {
                getListFromDatabase()
            }
            println("the sue is ${result1.await().size + result2.await().size}")
        }

        thread {
            println("the thread name is ${Thread.currentThread().name}")
        }
    }

    private fun getListFromFileSystem(): List<File> {
        return listOf()
    }

    private fun getListFromDatabase(): List<File> {
        return listOf()
    }

    private fun getList() = launch {
        listOf<File>()
    }
}