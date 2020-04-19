package com.wang.architecture.login.data

import android.util.AtomicFile
import com.wang.architecture.login.User
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object UserFile {

    fun save(user: User, path: File) {
        val atomicFile = AtomicFile(path)
        var outputStream: FileOutputStream? = null
        try {
            outputStream = atomicFile.startWrite()
            outputStream.write(user.name.toByteArray())
        } catch (e: IOException) {
            atomicFile.failWrite(outputStream)
        } finally {
            atomicFile.finishWrite(outputStream)
        }
    }

    fun restore(path: File): String {
        return AtomicFile(path).readFully().toString()
    }
}