package com.example.linusapp.dao

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface DistroApi {
    @GET("/distro")
    suspend fun getDistro(): Response<ResponseBody>
}