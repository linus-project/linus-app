package com.example.linusapp.vo

import com.google.gson.annotations.SerializedName

data class UserLoginVO(
    @SerializedName("idUser")
    val idUser: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("bornDate")
    val bornDate: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("adminKey")
    val adminKey: String,
    @SerializedName("imageCode")
    val imageCode: String,
    @SerializedName("fkLevel")
    val fkLevel: String,
    @SerializedName("isBlocked")
    val isBlocked: String
)
