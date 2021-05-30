package com.wang.mytest.clean

object FileMatchers {
    fun <T> allOf(vararg matchers: FileFilter<T>) = AllOf(matchers)
    fun <T> anyOf(vararg matchers: FileFilter<T>) = AnyOf(matchers)
    fun audioFile() = AudioFileMatcher()
    fun tempFile() = TempFileMatcher()
}

interface FileFilter<T> {
    fun matches(input: T): Boolean
}

class AnyOf<T>(private val matchers: Array<out FileFilter<T>>) : FileFilter<T> {
    override fun matches(input: T): Boolean {
        return matchers.all { matcher -> matcher.matches(input) }
    }
}

class AllOf<T>(private val matchers: Array<out FileFilter<T>>) : FileFilter<T> {
    override fun matches(input: T): Boolean {
        return matchers.any { matcher -> matcher.matches(input) }
    }
}

class AudioFileMatcher : FileFilter<String> {
    override fun matches(input: String): Boolean {
        return input.endsWith(".m4a")
                || input.endsWith(".mp3")
                || input.endsWith(".3gp")
    }
}

class TempFileMatcher : FileFilter<String> {
    override fun matches(input: String): Boolean {
        return input.endsWith(".temp")
    }
}