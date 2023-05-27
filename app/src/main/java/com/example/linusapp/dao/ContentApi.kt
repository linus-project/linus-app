package com.example.linusapp.dao

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentApi {
    @GET("/content/{idContent}")
    suspend fun getContent(@Path("idContent") idContent: Long): Response<ResponseBody>

    @GET("/content/level/{idLevel}")
    suspend fun getContentByLevel(@Path("idLevel") idLevel: Long): Response<ResponseBody>

    @GET("/content/favorite/level")
    suspend fun getFavoriteContentByLevel(
        @Query("idUser") idUser: Long,
        @Query("level") idLevel: Long
    ): Response<ResponseBody>
}