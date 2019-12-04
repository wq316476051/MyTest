package com.wang.mytest.test

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println(Thread.currentThread().name)
    repeat(100) {
        println(Thread.currentThread().name)
        launch {
            println(Thread.currentThread().name)
            delay(10L)
            print(".")
        }
    }
}