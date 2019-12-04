package com.wang.mytest.test

open class Person(val firstName: String) {

    val simple: Int
        get() = firstName.length

    var stringRe: String = firstName.toString()
        get() {
            return firstName.toString()
        }
        private set
}