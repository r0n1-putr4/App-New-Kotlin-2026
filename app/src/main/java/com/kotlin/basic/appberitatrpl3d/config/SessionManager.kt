package com.kotlin.basic.appberitatrpl3d.config

import android.content.Context
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences(
        "USER_SESSION",
        Context.MODE_PRIVATE
    )

    fun saveLogin(userId: String, username: String) {
        prefs.edit {

            putString("user_id", userId)
            putString("username", username)
            putBoolean("is_login", true)

        }
    }

    fun getUserId(): String? {
        return prefs.getString("user_id", null)
    }

    fun logout() {
        prefs.edit { clear() }
    }
}