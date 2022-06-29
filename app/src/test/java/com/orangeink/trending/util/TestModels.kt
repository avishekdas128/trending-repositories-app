package com.orangeink.trending.util

import com.orangeink.trending.feature_trending.data.remote.dto.BuiltByDto
import com.orangeink.trending.feature_trending.data.remote.dto.RepositoryDto
import com.orangeink.trending.feature_trending.domain.model.BuiltBy
import com.orangeink.trending.feature_trending.domain.model.Repository

val repository1 = Repository(
    id = 1,
    author = "meituan",
    builtBy = arrayListOf(BuiltBy("avatar")),
    currentPeriodStars = 24,
    description = "YOLOv6: a single stage object detection framework dedicated to industrial applications.",
    forks = 2535,
    language = "Python",
    languageColor = "#000000",
    name = "YOLOv6",
    stars = 56756
)

val repository2 = Repository(
    id = 2,
    author = "insidegui",
    builtBy = arrayListOf(BuiltBy("avatar")),
    currentPeriodStars = 678,
    description = "Virtualize macOS 12 and later on Apple Silicon",
    forks = 56778,
    language = "Swift",
    languageColor = "#FFFFFF",
    name = "VirtualBuddy",
    stars = 34556
)

val responseRepository = RepositoryDto(
    author = "D3Ext",
    name = "WEF",
    avatar = "https://github.com/D3Ext.png",
    url = "https://github.com/D3Ext/WEF",
    builtBy = arrayListOf(
        BuiltByDto(
            username = "D3Ext",
            avatar = "https://avatars.githubusercontent.com/u/67125397",
            href = "https://github.com/D3Ext"
        ),
        BuiltByDto(
            username = "ultrazar",
            avatar = "https://avatars.githubusercontent.com/u/48863881",
            href = "https://github.com/ultrazar"
        )
    ),
    currentPeriodStars = 70,
    description = "Wi-Fi Exploitation Framework",
    forks = 57,
    language = "Hack",
    languageColor = "#878787",
    stars = 543
)