package com.wang.architecture.login.data

import com.wang.architecture.login.User
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update

fun UserDatabaseHelper.getUser(): User = use {
    select(UserColumn.TABLE_NAME, UserColumn.AGE, UserColumn.IS_MALE)
            .whereArgs("${UserColumn.AGE} < {userAge}", "userAge" to 18)
            .parseSingle(rowParser { name: String, age: Int, male: Int ->
                User(name, age, male != 0)
            })
}

fun UserDatabaseHelper.setUser(user: User) = use {
    insert(UserColumn.TABLE_NAME, UserColumn.NAME to user.name, UserColumn.AGE to user.age,
            UserColumn.IS_MALE to if (user.isMale) 1 else 0)
}

fun UserDatabaseHelper.updateUser(user: User) = use {
    update(UserColumn.TABLE_NAME, UserColumn.NAME to user.name, UserColumn.AGE to user.age,
            UserColumn.IS_MALE to if (user.isMale) 1 else 0)
            .whereArgs("${UserColumn.NAME} = {userName}", "userName" to user.name)
            .exec()
}