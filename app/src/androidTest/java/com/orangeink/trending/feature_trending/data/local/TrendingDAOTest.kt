package com.orangeink.trending.feature_trending.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.orangeink.trending.feature_trending.data.local.entity.RepositoryEntity
import com.orangeink.trending.feature_trending.domain.model.BuiltBy
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class TrendingDAOTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: TrendingDatabase
    private lateinit var dao: TrendingDAO

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.getTrendingDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndDeleteRepository() = runBlocking {
        val repository = RepositoryEntity(
            id = 1,
            author = "test",
            name = "test",
            description = "test",
            language = null,
            languageColor = null,
            stars = 20,
            forks = 20,
            currentPeriodStars = 20,
            builtBy = arrayListOf(BuiltBy(avatar = "test"))
        )

        dao.insertAll(arrayListOf(repository))
        assertThat(dao.getTrendingRepositories()).contains(repository)

        dao.deleteAll()
        assertThat(dao.getTrendingRepositories().size).isEqualTo(0)
    }
}