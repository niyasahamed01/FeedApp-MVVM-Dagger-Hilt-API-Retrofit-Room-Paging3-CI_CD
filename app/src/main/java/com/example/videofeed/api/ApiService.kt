package com.example.videofeed.api

import com.example.videofeed.model.ResponseApi
import com.example.videofeed.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getAllUser(
        @Query("page") page: Int
    ): Response<ResponseApi>
}