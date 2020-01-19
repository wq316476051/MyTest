package com.wang.mytest.feature.storage.database

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.wang.mytest.feature.storage.database.table.Audio
import com.wang.mytest.feature.storage.database.table.Label
import com.wang.mytest.feature.storage.database.table.Speech
import com.wang.mytest.library.common.logd

/**
 * 升级
 */
class MyContentProvider : ContentProvider() {

    companion object {
        private const val TAG = "MyContentProvider"

        private const val AUDIO = 1
        private const val SPEECH = 2
        private const val LABEL = 3

        private val uriMatcher: UriMatcher by lazy {
            UriMatcher(UriMatcher.NO_MATCH).apply {
                addURI(AUTHORITY, "/$PATH_AUDIO", AUDIO)
                addURI(AUTHORITY, "/$PATH_SPEECH", SPEECH)
                addURI(AUTHORITY, "/$PATH_LABEL", LABEL)
            }
        }
    }

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(): Boolean {
        databaseHelper = DatabaseHelper(context!!)

//        val joinToString = arrayOf("1", "2").map { item -> "item $item" }.joinToString(prefix = "(", postfix = ")")
//        val joinToString2 = arrayOf("1", "2").joinToString(prefix = "(", postfix = ")") { item -> "item $item" }

        return true
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            AUDIO -> "audio/*"
            SPEECH -> "text/*"
            LABEL -> "text/*"
            else -> "*/*"
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        logd(TAG, "insert: uri = $uri");
        logd(TAG, "insert: id = ${uriMatcher.match(uri)}");
        return when (uriMatcher.match(uri)) {
            AUDIO -> {
                databaseHelper.writableDatabase.let {
                    val resultId = it.insert(Audio.TABLE_NAME, null, values)
                    val resultUri = ContentUris.withAppendedId(uri, resultId)
                    context?.contentResolver?.notifyChange(resultUri, null)
                    resultUri
                }
            }
            SPEECH -> {
                databaseHelper.writableDatabase.let {
                    val resultId = it.insert(Speech.TABLE_NAME, null, values)
                    val resultUri = ContentUris.withAppendedId(uri, resultId)
                    context?.contentResolver?.notifyChange(resultUri, null)
                    resultUri
                }
            }
            LABEL -> {
                databaseHelper.writableDatabase.let {
                    val resultId = it.insert(Label.TABLE_NAME, null, values)
                    logd(TAG, "insert: resultId = $resultId");
                    val resultUri = ContentUris.withAppendedId(uri, resultId)
                    context?.contentResolver?.notifyChange(resultUri, null)
                    resultUri
                }
            }
            else -> null
        }
    }

    @SuppressLint("Recycle")
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        logd(TAG, "query: uri = $uri");
        logd(TAG, "query: id = ${uriMatcher.match(uri)}");
        return when (uriMatcher.match(uri)) {
            AUDIO -> {
                databaseHelper.writableDatabase.query(Audio.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            }
            SPEECH -> {
                databaseHelper.writableDatabase.query(Speech.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            }
            LABEL -> {
                databaseHelper.writableDatabase.query(Label.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            }
            else -> null
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        // check something
        return when (uriMatcher.match(uri)) {
            AUDIO -> {
                databaseHelper.writableDatabase.update(Audio.TABLE_NAME, values, selection, selectionArgs)
            }
            SPEECH -> {
                databaseHelper.writableDatabase.update(Speech.TABLE_NAME, values, selection, selectionArgs)
            }
            LABEL -> {
                databaseHelper.writableDatabase.update(Label.TABLE_NAME, values, selection, selectionArgs)
            }
            else -> -1
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        // check something
        return when (uriMatcher.match(uri)) {
            AUDIO -> {
                databaseHelper.use {
                    delete(Audio.TABLE_NAME, selection, selectionArgs)
                }
            }
            SPEECH -> {
                databaseHelper.use {
                    delete(Speech.TABLE_NAME, selection, selectionArgs)
                }
            }
            LABEL -> {
                databaseHelper.use {
                    val delete = delete(Label.TABLE_NAME, selection, selectionArgs)
                    context!!.contentResolver.notifyChange(uri, null)
                    delete
                }
            }
            else -> -1
        }
    }
}