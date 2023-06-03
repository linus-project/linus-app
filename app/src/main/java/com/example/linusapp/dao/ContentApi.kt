package com.example.linusapp.dao

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("/content/favorite")
    suspend fun isFavorite(
        @Query("idUser") idUser: Long,
        @Query("idContent") idContent: Long
    ): Response<ResponseBody>

    @POST("/content/favorite")
    suspend fun favoriteContent(@Body user: RequestBody): Response<ResponseBody>

    @POST("/content/history")
    suspend fun saveHistoryContent(@Body user: RequestBody): Response<ResponseBody>

    @GET("/content/history")
    suspend fun getHistoryContent(@Query("idUser") idUser: Long): Response<ResponseBody>
}