package com.wang.mytest

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

class KotlinTest {
    @ExperimentalUnsignedTypes
    @Test
    fun test() {
        when (null) {
            3 -> {
                println("3")
            }
            null -> {
                println("null")
            }
            else -> {
                println("else")
            }
        }
    }

    @Test
    fun test2() {
        GlobalScope.launch {

        }
    }
}