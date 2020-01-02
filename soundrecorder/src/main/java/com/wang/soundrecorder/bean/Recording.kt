package com.wang.soundrecorder.bean

import java.io.File

class Recording(var file: File) {

    val filePath get() = file.absolutePath
    val name get() = file.name

    val tagList = mutableListOf<Tag>()
}