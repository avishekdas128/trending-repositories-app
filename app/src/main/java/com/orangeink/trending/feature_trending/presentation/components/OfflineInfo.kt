package com.orangeink.trending.feature_trending.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orangeink.trending.R

@Composable
fun OfflineInfo(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.error)
            .padding(8.dp),
        text = stringResource(id = R.string.offline),
        color = MaterialTheme.colors.onError,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        textAlign = TextAlign.Center
    )
}