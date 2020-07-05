package com.wang.mytest.feature.ui.surface_view

class Average {

    var count: Long = 0
    var sum: Long = 0

    fun update(value: Long) {
        count++
        sum += value
    }

    fun get(): Long {
        if (count == 0L) {
            return 0
        }
        return sum / count
    }
}