package com.orangeink.trending.network

import com.orangeink.trending.data.local.model.Repository
import retrofit2.Response
import retrofit2.http.GET

interface TrendingService {

    companion object {
        const val ENDPOINT = "https://github-trending-api-wonder.herokuapp.com/"
    }

    @GET("repositories")
    suspend fun repositories(): Response<List<Repository>>
}