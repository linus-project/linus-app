package com.example.linusapp.dao

import com.example.linusapp.vo.UserVO
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/login/username")
    suspend fun getLogin(@Body user: RequestBody) : Response<ResponseBody>
}