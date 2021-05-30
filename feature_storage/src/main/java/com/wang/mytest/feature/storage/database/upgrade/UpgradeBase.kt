package com.wang.mytest.feature.storage.database.upgrade

import android.database.sqlite.SQLiteDatabase
import androidx.annotation.CallSuper

abstract class UpgradeBase {

    abstract fun getVersion(): Int

    @CallSuper
    open fun onCreate(db: SQLiteDatabase) {
    }

    @CallSuper
    open fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}