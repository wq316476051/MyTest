package com.wang.soundrecorder.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.wang.soundrecorder.App

class SoundProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.wang.soundrecorder"
        private const val ID_RECORDING = 1
        private const val ID_RECORDING_ITEM = 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "recordings", ID_RECORDING)
            addURI(AUTHORITY, "recordings/#", ID_RECORDING_ITEM)
        }
    }

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(): Boolean {
        databaseHelper = DatabaseHelper(App.instance)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}