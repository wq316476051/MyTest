package com.wang.mytest.feature.storage.database.upgrade

import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.SqlType

/**
 * ALTER TABLE 语句用于在已有的表中添加、修改或删除列。
 * 1、在表中添加列
 *      ALTER TABLE table_name ADD column_name datatype
 * 2、要删除表中的列
 *      ALTER TABLE table_name DROP COLUMN column_name
 * 3、要改变表中列的数据类型
 *      ALTER TABLE table_name ALTER COLUMN column_name datatype
 */

/*
db.execSQL("ALTER TABLE ${Audio.TABLE_NAME} ADD COLUMN ${Audio.SIZE} INTEGER")
db.execSQL("ALTER TABLE ${Audio.TABLE_NAME} ADD COLUMN ${Audio.HASH_Code} INTEGER")
 */
fun SQLiteDatabase.addColumn(tableName: String, column: Pair<String, SqlType>) {
    val escapedTableName = tableName.replace("`", "``")
    execSQL(
            "ALTER TABLE $escapedTableName ADD COLUMN ${column.first} ${column.second.render()}"
    )
}

fun SQLiteDatabase.dropColumn(tableName: String, column: String) {
    val escapedTableName = tableName.replace("`", "``")
    execSQL(
            "ALTER TABLE $escapedTableName DROP COLUMN $column"
    )
}

fun SQLiteDatabase.alterColumn(tableName: String, column: Pair<String, SqlType>) {
    val escapedTableName = tableName.replace("`", "``")
    execSQL(
            "ALTER TABLE $escapedTableName ALTER COLUMN ${column.first} ${column.second.render()}"
    )
}