package com.wang.mytest.feature.storage.database.upgrade

import android.database.sqlite.SQLiteDatabase
import androidx.annotation.CallSuper
import com.wang.mytest.feature.storage.database.DatabaseHelper
import com.wang.mytest.feature.storage.database.table.Audio
import org.jetbrains.anko.db.*

/**
 * What has done in version 1:
 * 1. create audio table
 */
open class UpgradeFrom0To1 : UpgradeBase() {

    override fun getVersion() = 1

    override fun changeInThisVersion(db: SQLiteDatabase) {
        db.createTable(Audio.TABLE_NAME, true,
                Audio.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Audio.FILE_PATH to TEXT,
                Audio.FILE_NAME to TEXT,
                Audio.DURATION to INTEGER,
                Audio.LAST_MODIFIED to INTEGER)
    }
}