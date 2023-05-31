package com.example.linusapp.vo

data class DistroVO(
    var idDistro: Long,
    var distroName: String,
    var fkDistroBase: Long,
    var distroVersion: String,
    var fkLevel: Int,
    var image: Int
)