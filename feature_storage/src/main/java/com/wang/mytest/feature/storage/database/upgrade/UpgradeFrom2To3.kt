package com.wang.mytest.feature.storage.database.upgrade

import android.database.sqlite.SQLiteDatabase
import com.wang.mytest.feature.storage.database.DatabaseHelper
import com.wang.mytest.feature.storage.database.table.Audio
import com.wang.mytest.feature.storage.database.table.Label
import com.wang.mytest.feature.storage.database.table.Speech
import org.jetbrains.anko.db.*

/**
 * What changed in version 3:
 * 1. create speech table
 */
class UpgradeFrom2To3 : UpgradeFrom1To2() {

    override fun getVersion(): Int = 3

    override fun changeInThisVersion(db: SQLiteDatabase) {
        db.createTable(Speech.TABLE_NAME, true,
                Speech.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Speech.FILE_PATH to TEXT,
                Speech.CONTENT to TEXT,
                Speech.START_TIME to INTEGER,
                Speech.END_TIME to INTEGER)
    }
}