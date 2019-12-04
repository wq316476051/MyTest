package com.wang.mytest

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
}