package com.ratnez.myapplication.data

import com.ratnez.myapplication.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("?results=10")
    suspend fun getData(): Response<ApiResponse>
}