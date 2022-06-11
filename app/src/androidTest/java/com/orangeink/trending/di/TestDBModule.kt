package com.orangeink.trending.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.orangeink.trending.data.local.Converter
import com.orangeink.trending.data.local.TrendingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestDBModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context, gson: Gson) = run {
        Converter.initialize(gson)
        Room.inMemoryDatabaseBuilder(context, TrendingDatabase::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .allowMainThreadQueries()
            .build()
    }
}

