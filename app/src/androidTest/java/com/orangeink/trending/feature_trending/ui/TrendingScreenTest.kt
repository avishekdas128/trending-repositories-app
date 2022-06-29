package com.orangeink.trending.feature_trending.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.orangeink.trending.R
import com.orangeink.trending.feature_trending.presentation.TrendingList
import com.orangeink.trending.feature_trending.presentation.TrendingRepositoryState
import com.orangeink.trending.feature_trending.presentation.components.DrawableId
import com.orangeink.trending.feature_trending.presentation.components.RepositoryItem
import com.orangeink.trending.feature_trending.presentation.util.TestTags
import com.orangeink.trending.utils.repository1
import com.orangeink.trending.utils.repository2
import com.orangeink.trending.utils.repository3
import org.junit.Rule
import org.junit.Test

class TrendingScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun repositoryTitleDisplayedOnRepositoryItem() {
        composeRule.setContent {
            RepositoryItem(repository = repository1.toRepository())
        }

        composeRule.onNodeWithText(
            buildAnnotatedString {
                append(repository1.toRepository().author)
                append(" / ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(repository1.toRepository().name)
                }
            }.toString()
        ).assertIsDisplayed()
    }

    @Test
    fun repositoryLanguageNotDisplayedWhenNull() {
        composeRule.setContent {
            RepositoryItem(repository = repository3.toRepository())
        }

        composeRule.onNodeWithTag(TestTags.LanguageNode).assertDoesNotExist()
    }

    @Test
    fun repositoryListDisplaysAllRepositories() {
        val repositoryList = listOf(repository1, repository2, repository3)
        composeRule.setContent {
            TrendingList(
                state = TrendingRepositoryState(
                    trendingRepositories = repositoryList.map { it.toRepository() }
                )
            )
        }

        repositoryList.forEach {
            composeRule.onNodeWithText(it.description).assertIsDisplayed()
        }
    }

    @Test
    fun repositoryItemOnClickTrigger() {
        composeRule.setContent {
            RepositoryItem(repository = repository1.toRepository())
        }

        composeRule.onRoot().performClick()
        composeRule.onNode(SemanticsMatcher.expectValue(DrawableId, R.drawable.ic_heart_filled))
    }

}