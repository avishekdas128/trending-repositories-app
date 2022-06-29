package com.orangeink.trending.feature_trending.data.remote

import com.orangeink.trending.feature_trending.data.remote.dto.RepositoryDto
import retrofit2.Response
import retrofit2.http.GET

interface TrendingService {

    companion object {
        const val ENDPOINT = "https://github-trending-api-wonder.herokuapp.com/"
    }

    @GET("repositories")
    suspend fun getRepositories(): Response<List<RepositoryDto>>
}