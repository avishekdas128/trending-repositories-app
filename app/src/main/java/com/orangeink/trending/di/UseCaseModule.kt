package com.orangeink.trending.di

import com.orangeink.trending.feature_trending.domain.use_case.GetTrendingRepositories
import com.orangeink.trending.feature_trending.domain.use_case.GetTrendingRepositoriesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetTrendingRepositories(impl: GetTrendingRepositoriesImpl): GetTrendingRepositories

}