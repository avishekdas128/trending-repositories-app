package com.orangeink.trending.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.orangeink.trending.di.ApplicationScope
import com.orangeink.trending.data.local.model.Repository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Repository::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class TrendingDatabase : RoomDatabase() {

    abstract fun getTrendingDAO(): TrendingDAO

    class Callback @Inject constructor(
        private val database: Provider<TrendingDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}