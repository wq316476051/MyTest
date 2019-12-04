package com.wang.mytest.feature.audio

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TestKotlin {

    @Test
    fun test() {
        println(Thread.currentThread().name)
        println("1")
        runBlocking {
            println(Thread.currentThread().name)
            println("2")
            val async = async {
                println(Thread.currentThread().name)
                println("3")

            }
            async.await()
            println(async.getCompleted())
            println("4")
        }
        println("5")
    }
}