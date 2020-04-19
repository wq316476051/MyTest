package com.wang.mytest.clean

object FileMatchers {
    fun <T> allOf(vararg matchers: FileMatcher<T>) = AllOf(matchers)
    fun <T> anyOf(vararg matchers: FileMatcher<T>) = AnyOf(matchers)
    fun audioFile() = AudioFileMatcher()
    fun tempFile() = TempFileMatcher()
}

interface FileMatcher<T> {
    fun matches(input: T): Boolean
}

class AnyOf<T>(private val matchers: Array<out FileMatcher<T>>) : FileMatcher<T> {
    override fun matches(input: T): Boolean {
        return matchers.all { matcher -> matcher.matches(input) }
    }
}

class AllOf<T>(private val matchers: Array<out FileMatcher<T>>) : FileMatcher<T> {
    override fun matches(input: T): Boolean {
        return matchers.any { matcher -> matcher.matches(input) }
    }
}

class AudioFileMatcher : FileMatcher<String> {
    override fun matches(input: String): Boolean {
        return input.endsWith(".m4a")
                || input.endsWith(".mp3")
                || input.endsWith(".3gp")
    }
}

class TempFileMatcher : FileMatcher<String> {
    override fun matches(input: String): Boolean {
        return input.endsWith(".temp")
    }
}