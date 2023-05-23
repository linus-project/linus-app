package com.example.linusapp.utils

import com.example.linusapp.dao.NewsApi
import com.example.linusapp.dao.UserApi
import retrofit2.Retrofit

object Api {
    var BASE_URL = "http://3.222.160.241"

    fun getUserApi() : UserApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(UserApi::class.java)
    }
    fun getNewsApi() : NewsApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(NewsApi::class.java)
    }

}