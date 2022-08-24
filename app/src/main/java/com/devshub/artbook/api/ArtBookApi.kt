package com.devshub.artbook.api

import com.devshub.artbook.model.ImageResponse
import com.devshub.artbook.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtBookApi {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = API_KEY
    ): Response<ImageResponse>

}