package com.wang.mytest

import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    private val MESSAGE = "This is a test"
    private val PACKAGE_NAME = "com.example.myfirstapp"
    @Rule
    private val intentsRule: IntentsTestRule<MainActivity>
            = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    public fun test() {
    }
}