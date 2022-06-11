package com.orangeink.trending.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.orangeink.trending.data.local.Converter
import com.orangeink.trending.data.local.TrendingDAO
import com.orangeink.trending.data.local.TrendingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideDatabase(
        gson: Gson,
        application: Application,
        callback: TrendingDatabase.Callback
    ): TrendingDatabase {
        Converter.initialize(gson)
        return Room.databaseBuilder(application, TrendingDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideRecentSearchDAO(db: TrendingDatabase): TrendingDAO {
        return db.getTrendingDAO()
    }
}