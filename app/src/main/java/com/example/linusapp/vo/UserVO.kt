package com.example.linusapp.vo

import com.google.gson.annotations.SerializedName

data class UserVO(
    var idUser: Long,
    var name: String,
    var username: String,
    var email: String,
    var password: String,
    var genre: String,
    var bornDate: String,
    var phoneNumber: String,
    var adminKey: String,
    var imageCode: String,
    var fkLevel: Long,
    var isBlocked: Int
)
