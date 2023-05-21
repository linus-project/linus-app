package com.example.linusapp.utils

import com.example.linusapp.dao.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    var BASE_URL = "http://3.222.160.241"

    fun getUserApi() : UserApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(UserApi::class.java)
    }
}