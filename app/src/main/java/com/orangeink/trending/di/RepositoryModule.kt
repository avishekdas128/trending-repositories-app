package com.orangeink.trending.di

import com.orangeink.trending.feature_trending.data.repository.TrendingRepositoryImpl
import com.orangeink.trending.feature_trending.domain.repository.TrendingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTrendingRepository(impl: TrendingRepositoryImpl): TrendingRepository

}