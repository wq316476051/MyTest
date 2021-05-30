package com.wang.mytest.feature.storage.database.upgrade

import android.database.sqlite.SQLiteDatabase
import com.wang.mytest.feature.storage.database.table.Audio
import com.wang.mytest.feature.storage.database.table.Label
import org.jetbrains.anko.db.*

/**
 * What changed in version 2:
 * 1. alter audio table, add two columns
 * 2. create label table
 */
open class UpgradeFrom1To2 : UpgradeFrom0To1() {

    override fun getVersion(): Int = 2

    open override fun onCreate(db: SQLiteDatabase) {
        super.onCreate(db)
        createLabelTable(db)
    }

    open override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        createLabelTable(db)
    }

    /**
     * ALTER TABLE 语句用于在已有的表中添加、修改或删除列。
     * 1、在表中添加列
     *      ALTER TABLE table_name ADD column_name datatype
     * 2、要删除表中的列
     *      ALTER TABLE table_name DROP COLUMN column_name
     * 3、要改变表中列的数据类型
     *      ALTER TABLE table_name ALTER COLUMN column_name datatype
     */
    private fun createLabelTable(db: SQLiteDatabase) {
        db.addColumn(Audio.TABLE_NAME, Audio.SIZE to INTEGER)
        db.addColumn(Audio.TABLE_NAME, Audio.HASH_Code to INTEGER)

        db.createTable(Label.TABLE_NAME, true,
                Label.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Label.FILE_PATH to TEXT,
                Label.CONTENT to TEXT,
                Label.TIME to INTEGER)
    }
}