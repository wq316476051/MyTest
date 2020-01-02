package com.wang.soundrecorder.provider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "recordings.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE recordings(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, path TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}

class MigrationFrom1To2 {

}

class MigrationFrom2To3 {

}