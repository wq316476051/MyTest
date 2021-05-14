package com.wang.mytest.feature.storage.database.upgrade

import android.database.sqlite.SQLiteDatabase
import com.wang.mytest.feature.storage.database.table.Speech
import com.wang.mytest.common.logd
import org.jetbrains.anko.db.*

/**
 * What changed in version 3:
 * 1. create speech table
 */
class UpgradeFrom2To3 : UpgradeFrom1To2() {

    override fun getVersion(): Int = 3

    open override fun onCreate(db: SQLiteDatabase) {
        super.onCreate(db)
        logd("UpgradeFrom2To3", "onCreate: ")
        createSpeechTable(db)
    }

    open override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        logd("UpgradeFrom2To3", "onUpgrade: ")
        if (oldVersion < getVersion()) {
            createSpeechTable(db)
        }
    }

    private fun createSpeechTable(db: SQLiteDatabase) {
        logd("UpgradeFrom2To3", "createSpeechTable: ")
        db.createTable(Speech.TABLE_NAME, true,
                Speech.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Speech.FILE_PATH to TEXT,
                Speech.CONTENT to TEXT,
                Speech.START_TIME to INTEGER,
                Speech.END_TIME to INTEGER)
    }
}