package com.example.tea1.data

import android.content.Context
import com.google.gson.Gson
import java.io.File

object DataProvider {
    private val gson = Gson()
    private const val FILE_SUFFIX = "_profile.json"

    fun getUserProfile(context: Context, user: String): UserTodos {
        val file = File(context.filesDir, getFilename(user))
        if (!file.exists()) {
            return UserTodos(user)
        }
        return context.openFileInput(getFilename(user)).bufferedReader().use { reader ->
            val jsonString = reader.readText()
            gson.fromJson(jsonString, UserTodos::class.java)
        }
    }

    fun saveUserProfile(context: Context, userTodos: UserTodos) {
        val jsonString = gson.toJson(userTodos)

        context.openFileOutput(getFilename(userTodos.user), Context.MODE_PRIVATE).use { output ->
            output.write(jsonString.toByteArray())
        }
    }

    fun getUsers(context: Context): List<String> {
        val files: Array<String> = context.fileList()
        return files.filter{file -> file.endsWith(FILE_SUFFIX)}.map{file -> getUserFromFilename(file)}
    }

    fun deleteUserProfile(context: Context, user: String): Boolean {
        val file = File(context.filesDir, getFilename(user))
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }

    fun deleteAllUserProfiles(context: Context): Boolean {
        var result = true
        for (user in getUsers(context)) {
            result = result && deleteUserProfile(context, user)
        }
        return result
    }

    private fun getFilename(user: String): String {
        return user + FILE_SUFFIX
    }

    private fun getUserFromFilename(filename: String): String {
        return filename.removeSuffix(FILE_SUFFIX)
    }
}