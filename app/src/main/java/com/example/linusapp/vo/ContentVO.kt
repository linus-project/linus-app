package com.example.linusapp.vo

data class ContentVO(
    var idContent: Long = 0,
    var contentTitle: String = "",
    var content: String = "",
    var fkDistro: Long = 0,
    var fkLevel: Int = 0,
    var image: Int = 0,
    var videoPath: String = ""
)
