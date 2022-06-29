package com.orangeink.trending.ui

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import com.orangeink.trending.R
import com.orangeink.trending.feature_trending.data.local.TrendingDatabase
import com.orangeink.trending.feature_trending.presentation.TrendingActivity
import com.orangeink.trending.feature_trending.presentation.components.DrawableId
import com.orangeink.trending.feature_trending.presentation.util.TestTags
import com.orangeink.trending.utils.responseRepository
import com.orangeink.trending.utils.successfulResponse
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TrendingRepositoryAppTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<TrendingActivity>()

    @Inject
    lateinit var mockServer: MockWebServer

    @Inject
    lateinit var database: TrendingDatabase

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
        database.close()
    }

    @Test
    fun loadingRepositoriesAndSearchingWorksCorrectly() {
        enqueueResponse()

        composeRule.waitUntil {
            composeRule.onAllNodesWithText(responseRepository.description)
                .fetchSemanticsNodes().size == 1
        }
        composeRule.onNodeWithText(responseRepository.description).performClick()
        composeRule.onNode(SemanticsMatcher.expectValue(DrawableId, R.drawable.ic_heart_filled))

        composeRule.onNodeWithText(responseRepository.description).performClick()
        composeRule.onNode(SemanticsMatcher.expectValue(DrawableId, R.drawable.ic_heart))

        composeRule.onNodeWithContentDescription(
            ApplicationProvider.getApplicationContext<Context>().getString(R.string.search)
        ).performClick()
        composeRule.onNodeWithTag(TestTags.SearchEditText).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.SearchEditText).performTextInput("hg")
        composeRule.waitUntil {
            composeRule.onAllNodesWithText(responseRepository.description)
                .fetchSemanticsNodes().isEmpty()
        }

        composeRule.onNodeWithContentDescription(
            ApplicationProvider.getApplicationContext<Context>().getString(R.string.back)
        ).performClick()
        composeRule.waitUntil {
            composeRule.onAllNodesWithText(responseRepository.description)
                .fetchSemanticsNodes().size == 1
        }
    }

    private fun enqueueResponse() {
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(successfulResponse)
        )
    }
}