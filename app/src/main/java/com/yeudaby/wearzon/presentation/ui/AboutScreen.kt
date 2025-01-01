package com.yeudaby.wearzon.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Text
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.google.android.horologist.compose.material.Title
import com.yeudaby.wearzon.R

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun AboutScreen() {
    val columnState = rememberResponsiveColumnState(
        contentPadding = ScalingLazyColumnDefaults.padding(
            first = ScalingLazyColumnDefaults.ItemType.Text,
            last = ScalingLazyColumnDefaults.ItemType.Text
        )
    )
    ScreenScaffold(scrollState = columnState) {
        ScalingLazyColumn(
            columnState = columnState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                ResponsiveListHeader(contentPadding = firstItemPadding()) {
                    Title(text = stringResource(R.string.about))
                }
            }

            item {
                Card(
                    onClick = {},
                    backgroundPainter = painterResource(R.drawable.candle_bg),
                    contentColor = Color.White
                ) {
                    Text(stringResource(R.string.in_honor))
                }
            }

            item {
                Card(
                    onClick = {},
                ) {
                    Text(stringResource(R.string.about_app), fontWeight = FontWeight.ExtraLight)
                }

            }

            item {
                Card(onClick = {}) {
                    Text(stringResource(R.string.about_me))
                }
            }
        }
    }
}