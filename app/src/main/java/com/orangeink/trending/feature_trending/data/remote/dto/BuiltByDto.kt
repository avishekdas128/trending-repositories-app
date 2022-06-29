package com.orangeink.trending.feature_trending.data.remote.dto

import com.orangeink.trending.feature_trending.domain.model.BuiltBy

data class BuiltByDto(
    val avatar: String,
    val href: String,
    val username: String
) {
    fun toBuiltBy(): BuiltBy {
        return BuiltBy(
            avatar = avatar
        )
    }
}