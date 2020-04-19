package com.wang.architecture.login.data

import com.wang.architecture.login.User
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object UserRemote {

    fun getUser(path: String): User? {
        val connection = URL("http://1112.212.12.12:1212").openConnection() as HttpURLConnection
        if (connection.responseCode == 200) {
            connection.responseMessage

            val jsonObject = JSONObject(connection.responseMessage)
            val name = jsonObject.optString("name")
            val age = jsonObject.optInt("age")
            val isMale = jsonObject.optBoolean("is_male")
            return User(name, age, isMale)
        }
        return null
    }
}