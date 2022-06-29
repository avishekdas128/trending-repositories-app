package com.orangeink.trending.feature_trending.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.orangeink.trending.feature_trending.data.local.entity.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TrendingDatabase : RoomDatabase() {

    abstract fun getTrendingDAO(): TrendingDAO
}