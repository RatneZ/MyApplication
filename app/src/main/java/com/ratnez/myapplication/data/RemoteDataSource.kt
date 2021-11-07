package com.ratnez.myapplication.data

import com.ratnez.myapplication.model.ApiResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getData(): Response<ApiResponse> {
        return apiService.getData()
    }
}