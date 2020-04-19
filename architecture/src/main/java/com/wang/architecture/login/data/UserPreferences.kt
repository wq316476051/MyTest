package com.wang.architecture.login.data

import android.content.Context
import android.content.SharedPreferences
import com.wang.architecture.App

object UserPreferences {

    private const val LAST_USER_ID = "last_user_id"

    private val preferences: SharedPreferences by lazy {
        App.getSharedPreferences("user", Context.MODE_PRIVATE)
    }

    fun getLastUserId(): String? = preferences.getString(LAST_USER_ID, null)

    fun setLastUserId(userId: String): Boolean = preferences.edit().putString(LAST_USER_ID, userId).commit()
}
