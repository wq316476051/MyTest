package com.wang.mytest.feature.storage.database.upgrade

import android.database.sqlite.SQLiteDatabase
import androidx.annotation.CallSuper
import com.wang.mytest.common.logd

abstract class UpgradeBase {

    abstract fun getVersion(): Int

    @CallSuper
    open fun onCreate(db: SQLiteDatabase) {
        logd("UpgradeBase", "onCreate: ")
    }

    @CallSuper
    open fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        logd("UpgradeBase", "onUpgrade: ")
    }
}