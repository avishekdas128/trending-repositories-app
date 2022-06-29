package com.orangeink.trending.feature_trending.data.local

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.orangeink.trending.utils.repository1
import com.orangeink.trending.utils.repository2
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class TrendingDAOTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
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
    fun insertAllRepositories() = runTest {
        dao.insertAll(arrayListOf(repository1, repository2))
        assertThat(dao.getTrendingRepositories()).contains(repository1)
        assertThat(dao.getTrendingRepositories()).contains(repository2)

        assertThat(dao.getTrendingRepositories().size).isEqualTo(2)
    }

    @Test
    fun deleteAllRepositories() = runTest {
        dao.insertAll(arrayListOf(repository1, repository2))
        assertThat(dao.getTrendingRepositories().size).isEqualTo(2)

        dao.deleteAll()
        assertThat(dao.getTrendingRepositories().size).isEqualTo(0)
    }

    @Test
    fun getAllRepositories() = runTest {
        dao.insertAll(arrayListOf(repository1, repository2))
        assertThat(dao.getTrendingRepositories().size).isEqualTo(2)
    }
}