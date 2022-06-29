package com.orangeink.trending.feature_trending.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.orangeink.trending.feature_trending.data.local.entity.RepositoryEntity

@Dao
interface TrendingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repositories: List<RepositoryEntity>)

    @Query("DELETE FROM trending_repositories")
    suspend fun deleteAll()

    @Query("SELECT * FROM trending_repositories")
    suspend fun getTrendingRepositories(): List<RepositoryEntity>
}