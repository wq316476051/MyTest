package com.wang.mytest.feature.storage.database.upgrade

import android.database.sqlite.SQLiteDatabase
import com.wang.mytest.common.util.LogUtils
import com.wang.mytest.feature.storage.database.table.Speech
import org.jetbrains.anko.db.*

/**
 * What changed in version 3:
 * 1. create speech table
 */
class UpgradeFrom2To3 : UpgradeFrom1To2() {

    override fun getVersion(): Int = 3

    open override fun onCreate(db: SQLiteDatabase) {
        super.onCreate(db)
        createSpeechTable(db)
    }

    open override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        if (oldVersion < getVersion()) {
            createSpeechTable(db)
        }
    }

    private fun createSpeechTable(db: SQLiteDatabase) {
        LogUtils.debug("UpgradeFrom2To3", "createSpeechTable: ")
        db.createTable(Speech.TABLE_NAME, true,
                Speech.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Speech.FILE_PATH to TEXT,
                Speech.CONTENT to TEXT,
                Speech.START_TIME to INTEGER,
                Speech.END_TIME to INTEGER)
    }
}