package com.kotlin.basic.appberitatrpl3d.models

data class LoginModel(
    val success: Boolean,
    val message: String,
    val user: User
) {

    data class User(
        val id: String,
        val fullname: String,
        val username: String,
        val email: String
    )
}