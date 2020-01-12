package com.wang.mytest.feature.storage.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.wang.mytest.feature.storage.database.upgrade.UpgradeBase
import com.wang.mytest.feature.storage.database.upgrade.UpgradeFrom2To3
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

class DatabaseHelper(context: Context) : ManagedSQLiteOpenHelper(
        context, "recorder", version = upgradeBase.getVersion()) {

    companion object {
        const val TAG = "DatabaseHelper"
        val upgradeBase: UpgradeBase = UpgradeFrom2To3()
    }

    /**
     * 数据库初始 version 为 0，returned by getVersion()
     * 当 version == 0 时，调用 onCreate()
     */
    override fun onCreate(db: SQLiteDatabase) {
        upgradeBase.onCreate(db)
    }

    /**
     * 当 newVersion > getVersion() 时，调用 onUpgrade()
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        upgradeBase.onUpgrade(db, oldVersion, newVersion)
    }
}
