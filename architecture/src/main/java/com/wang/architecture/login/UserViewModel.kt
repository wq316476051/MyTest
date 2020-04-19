package com.wang.architecture.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.wang.architecture.login.data.*

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userCache = UserCache

    private val preferences: UserPreferences = UserPreferences
    private val file: UserFile = UserFile
    private val database: UserDatabaseHelper = application.userDatabase

    private val remote: UserRemote = UserRemote

    private val repository = UserRepository(userCache, database, remote)

    fun getUser(userId: String): LiveData<User> {
        return repository.getUser(userId)
    }
}