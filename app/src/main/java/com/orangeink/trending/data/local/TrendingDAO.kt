package com.orangeink.trending.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.orangeink.trending.data.local.model.Repository

@Dao
interface TrendingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repositories: List<Repository>)

    @Query("DELETE FROM trending_repositories")
    suspend fun deleteAll()

    @Query("SELECT * FROM trending_repositories")
    fun getTrendingRepositories(): LiveData<List<Repository>>
}