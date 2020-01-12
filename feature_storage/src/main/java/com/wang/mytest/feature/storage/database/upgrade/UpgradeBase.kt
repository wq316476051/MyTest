package com.wang.mytest.feature.storage.database.upgrade

import android.database.sqlite.SQLiteDatabase
import androidx.annotation.CallSuper

abstract class UpgradeBase {

    abstract fun getVersion(): Int

    abstract fun changeInThisVersion(db: SQLiteDatabase)

    @CallSuper
    open fun onCreate(db: SQLiteDatabase) {
        changeInThisVersion(db)
    }

    @CallSuper
    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < getVersion()) {
            changeInThisVersion(db)
        }
    }
}