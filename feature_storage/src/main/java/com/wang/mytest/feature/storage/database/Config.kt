package com.wang.mytest.feature.storage.database

import android.net.Uri

const val SCHEME = "content"
const val AUTHORITY = "com.wang.mytest.feature.storage"
const val PATH_AUDIO = "audio"
const val PATH_SPEECH = "speech"
const val PATH_LABEL = "label"

val AUDIO_URI: Uri = Uri.Builder().scheme(SCHEME).authority(AUTHORITY).path(PATH_AUDIO).build()
val SPEECH_URI: Uri = Uri.Builder().scheme(SCHEME).authority(AUTHORITY).path(PATH_SPEECH).build()
val LABEL_URI: Uri = Uri.Builder().scheme(SCHEME).authority(AUTHORITY).path(PATH_LABEL).build()