package com.wang.mytest.loader

import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.loader.content.CursorLoader

/*
Loader
    AsyncTaskLoader
        CursorLoader
 */
class SpeechLoader(context: Context) : CursorLoader(
        context, Uri.parse(""), null, null, null, null) {

    override fun onStartLoading() {
        super.onStartLoading()
    }

    override fun onStopLoading() {
        super.onStopLoading()
    }

    override fun onAbandon() {
        super.onAbandon()
    }

    override fun onCancelLoad(): Boolean {
        return super.onCancelLoad()
    }

    override fun onForceLoad() {
        super.onForceLoad()
    }

    override fun onLoadInBackground(): Cursor? {
        return super.onLoadInBackground()
    }
}
