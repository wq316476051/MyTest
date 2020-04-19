package com.wang.architecture.login.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.*

val Context.userDatabase: UserDatabaseHelper
    get() = UserDatabaseHelper.getInstance(this)

class UserDatabaseHelper private constructor(context: Context)
    : ManagedSQLiteOpenHelper(context, UserColumn.DB_NAME, null, 1) {

    companion object {
        private const val TAG = "UserDatabaseHelper"

        private var instance: UserDatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context) = instance ?: UserDatabaseHelper(context.applicationContext)
    }

    init {
        instance = this
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        Log.d(TAG, "onConfigure: ")
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(UserColumn.TABLE_NAME, true,
                UserColumn.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT + UNIQUE,
                UserColumn.NAME to TEXT,
                UserColumn.AGE to INTEGER,
                UserColumn.IS_MALE to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(UserColumn.TABLE_NAME, true)
    }
}