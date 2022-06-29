package com.orangeink.trending.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.orangeink.trending.feature_trending.data.local.Converters
import com.orangeink.trending.feature_trending.data.local.TrendingDAO
import com.orangeink.trending.feature_trending.data.local.TrendingDatabase
import com.orangeink.trending.feature_trending.data.util.GsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestDBModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideTrendingDatabase(
        @ApplicationContext context: Context,
        gson: Gson
    ): TrendingDatabase {
        return Room.inMemoryDatabaseBuilder(context, TrendingDatabase::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .addTypeConverter(Converters(GsonParser(gson)))
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideTrendingDAO(db: TrendingDatabase): TrendingDAO {
        return db.getTrendingDAO()
    }
}

