package com.example.tea1.data

import android.content.Context
import com.google.gson.Gson
import java.io.File

    object DataProvider {
    private val gson = Gson()
    private const val FILE_SUFFIX = "_profile.json"

    fun getUserProfile(context: Context, user: String): UserProfile {
        val file = File(context.filesDir, getFilename(user))
        if (!file.exists()) {
            return UserProfile(user)
        }
        return context.openFileInput(getFilename(user)).bufferedReader().use { reader ->
            val jsonString = reader.readText()
            gson.fromJson(jsonString, UserProfile::class.java)
        }
    }

    fun saveUserProfile(context: Context, userProfile: UserProfile) {
        val jsonString = gson.toJson(userProfile)

        context.openFileOutput(getFilename(userProfile.user), Context.MODE_PRIVATE).use { output ->
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

    fun deleteUserProfiles(context: Context, users: List<String>): Boolean {
        var result = true
        for (user in users) {
            result = result && deleteUserProfile(context, user)
        }
        return result
    }

    fun deleteAllUserProfiles(context: Context): Boolean {
        return deleteUserProfiles(context, getUsers(context))
    }

    private fun getFilename(user: String): String {
        return user + FILE_SUFFIX
    }

    private fun getUserFromFilename(filename: String): String {
        return filename.removeSuffix(FILE_SUFFIX)
    }
}