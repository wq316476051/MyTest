package com.wang.mytest.feature.storage.database.upgrade

import android.database.sqlite.SQLiteDatabase
import com.wang.mytest.feature.storage.database.table.Audio
import com.wang.mytest.library.common.logd
import org.jetbrains.anko.db.*

/**
 * What has done in version 1:
 * 1. create audio table
 */
open class UpgradeFrom0To1 : UpgradeBase() {

    override fun getVersion() = 1

    open override fun onCreate(db: SQLiteDatabase) {
        super.onCreate(db)
        logd("UpgradeFrom0To1", "onCreate: ")
        createAudioTable(db)
    }

    open override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        logd("UpgradeFrom0To1", "onUpgrade: ")
        if (oldVersion < getVersion()) {
            createAudioTable(db)
        }
    }

    private fun createAudioTable(db: SQLiteDatabase) {
        logd("UpgradeFrom0To1", "createAudioTable: ")
        db.createTable(Audio.TABLE_NAME, true,
                Audio.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Audio.FILE_PATH to TEXT,
                Audio.FILE_NAME to TEXT,
                Audio.DURATION to INTEGER,
                Audio.LAST_MODIFIED to INTEGER)
    }
}