package com.example.linusapp.dao

import com.example.linusapp.vo.UserVO
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {
    @GET("/news")
    suspend fun getNews() : Response<ResponseBody>
}