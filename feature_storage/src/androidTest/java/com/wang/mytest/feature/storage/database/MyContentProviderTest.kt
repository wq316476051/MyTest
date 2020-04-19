package com.wang.mytest.feature.storage.database

import android.content.ContentValues
import android.database.DatabaseUtils
import android.net.Uri
import android.test.ProviderTestCase2
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyContentProviderTest() : ProviderTestCase2<MyContentProvider>(MyContentProvider::class.java, AUTHORITY) {

    @Test
    fun cloneSharedPreferences() {
        val cursor = provider.query(Uri.parse(""), null, null, null, null)
        Assert.assertNotNull(cursor)
        Assert.assertTrue(cursor!!.count == 1)
        cursor.moveToFirst()
        val str = cursor.getString(cursor.getColumnIndex(""))

        val values = ContentValues()
        DatabaseUtils.cursorRowToContentValues(cursor, values)

        val insert = provider.insert(Uri.parse(""), values)
    }
}