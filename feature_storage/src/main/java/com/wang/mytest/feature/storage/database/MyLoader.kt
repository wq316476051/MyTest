package com.wang.mytest.feature.storage.database

import android.content.Context
import android.net.Uri
import androidx.loader.content.CursorLoader

class MyLoader(context: Context) : CursorLoader(context,
        Uri.Builder().scheme(SCHEME).authority(AUTHORITY).path(PATH_AUDIO).build(),
        null, null, null, null)