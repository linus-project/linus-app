package com.example.linusapp.vo

data class ContentVO(
    var idContent: Long,
    var contentTitle: String,
    var content: String,
    var fkDistro: Long,
    var fkLevel: Int,
    var image: Int,
    var videoPath: String
)
