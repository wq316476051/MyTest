package com.wang.architecture.login.data

import android.util.LruCache
import androidx.lifecycle.LiveData
import com.wang.architecture.login.User

object UserCache {

    private val cache: LruCache<String, LiveData<User>> by lazy {
        object : LruCache<String, LiveData<User>>(10) {
            override fun sizeOf(key: String?, value: LiveData<User>?): Int {
                return super.sizeOf(key, value) // 默认返回1，表示最大存储 maxSize 个对象
            }
        }
    }

    fun getUser(userId: String): LiveData<User>? {
        return cache.get(userId)
    }

    fun putUser(userId: String, user: LiveData<User>) {
        cache.put(userId, user)
    }
}