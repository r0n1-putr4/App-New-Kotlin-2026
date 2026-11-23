package com.kotlin.basic.appberitatrpl3d.config

import android.content.Context
import android.content.Intent
import androidx.core.content.edit
import com.kotlin.basic.appberitatrpl3d.pages.LoginActivity

class SessionManager(val context: Context) {
    private val prefs = context.getSharedPreferences(
        "USER_LOGIN",
        Context.MODE_PRIVATE
    )

    fun saveLogin(
        id: String, fullname: String,
        username: String, email: String
    ) {
        prefs.edit {
            putString("id",id)
            putString("fullname",fullname)
            putString("username",username)
            putString("email",email)
            putBoolean("is_login",true)
        }
    }
    fun isLogin(): Boolean = prefs.getBoolean("is_login",false)
    fun cekLogin(){
        if (!isLogin()){
            context.startActivity(Intent(context,
                LoginActivity::class.java))
        }
    }
    fun getId(): String? = prefs.getString("id",null)
    fun getFullName(): String? = prefs.getString("fullname",
        null)
    fun getUserName(): String? = prefs.getString("username",null)
    fun getEmail(): String? = prefs.getString("email",null)
    fun logOut(){
        prefs.edit { clear() }
    }

}