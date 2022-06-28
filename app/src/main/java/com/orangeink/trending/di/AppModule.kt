package com.orangeink.trending.di

import android.app.Application
import com.orangeink.trending.core.util.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        application: Application
    ): NetworkMonitor {
        return NetworkMonitor(application)
    }
}