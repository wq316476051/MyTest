package com.wang.architecture.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wang.architecture.login.data.*

class UserRepository(private val cache: UserCache,
                     private val database: UserDatabaseHelper,
                     private val remote: UserRemote) {

    private val rateLimiter: RateLimiter = RateLimiter()

    fun getUser(userId: String): LiveData<User> {
        // 1. From cache
        val user = cache.getUser(userId)
        if (user != null) {
            return user
        }
        val data = MutableLiveData<User>()
        cache.putUser(userId, data)

        // 2. From database
        data.postValue(database.getUser())

        // 3. From web
        if (rateLimiter.canFetch()) {
            data.postValue(remote.getUser(""))
        }

        return data
    }
}