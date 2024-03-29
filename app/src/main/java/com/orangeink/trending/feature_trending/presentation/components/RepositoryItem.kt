package com.orangeink.trending.feature_trending.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.orangeink.trending.R
import com.orangeink.trending.feature_trending.domain.model.Repository
import com.orangeink.trending.feature_trending.presentation.util.TestTags
import java.text.NumberFormat
import java.util.Locale

val DrawableId = SemanticsPropertyKey<Int>("DrawableResId")
var SemanticsPropertyReceiver.drawableId by DrawableId

@Composable
fun RepositoryItem(
    repository: Repository,
    modifier: Modifier = Modifier
) {
    var isSelected by rememberSaveable {
        mutableStateOf(false)
    }
    val heartIcon = if (isSelected)
        R.drawable.ic_heart_filled
    else
        R.drawable.ic_heart
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                isSelected = !isSelected
            }
            .background(
                color = MaterialTheme.colors.primaryVariant
            )
            .padding(16.dp, 12.dp, 16.dp, 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_trending),
                contentDescription = stringResource(id = R.string.trending)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = buildAnnotatedString {
                    append(repository.currentPeriodStars.toString())
                    append(" stars today")
                },
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = modifier.semantics { drawableId = heartIcon },
                painter = painterResource(id = heartIcon),
                contentDescription = null,
                tint = if (isSelected)
                    MaterialTheme.colors.error
                else
                    MaterialTheme.colors.primary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = buildAnnotatedString {
                append(repository.author)
                append(" / ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(repository.name)
                }
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = MaterialTheme.colors.secondary
        )
        if (repository.description.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = repository.description,
                fontSize = 11.sp,
                color = MaterialTheme.colors.onSecondary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        val lazyRowState = rememberLazyListState()
        LazyRow(
            state = lazyRowState,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(repository.builtBy.size) { i ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(repository.builtBy[i].avatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.avatar_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            repository.language?.let {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .testTag(TestTags.LanguageNode),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(
                                color = try {
                                    Color(
                                        android.graphics.Color.parseColor(repository.languageColor)
                                    )
                                } catch (e: Exception) {
                                    Color.White
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = it,
                        fontSize = 11.sp,
                        color = MaterialTheme.colors.onSecondary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Row(
                modifier = Modifier.weight(1f, fill = false),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StarsAndFork(value = repository.stars, drawable = R.drawable.ic_star)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier.weight(1f, fill = false),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StarsAndFork(value = repository.forks, drawable = R.drawable.ic_fork)
            }
        }
    }
}

@Composable
fun StarsAndFork(
    value: Int,
    @DrawableRes drawable: Int
) {
    Icon(
        painter = painterResource(id = drawable),
        contentDescription = null,
        tint = MaterialTheme.colors.onSecondary
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
        text = NumberFormat.getNumberInstance(Locale.US).format(value),
        fontSize = 11.sp,
        color = MaterialTheme.colors.onSecondary
    )
}