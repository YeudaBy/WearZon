package com.yeudaby.wearzon.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.Button
import com.google.android.horologist.compose.material.Chip
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.yeudaby.wearzon.R
import com.yeudaby.wearzon.presentation.data.PrayerOption


@OptIn(ExperimentalHorologistApi::class)
@Composable
fun ListScreen(
    navigateToSettings: () -> Unit,
    navigateToInfo: () -> Unit,
    onItemClick: (id: String) -> Unit,
) {
    val columnState = rememberResponsiveColumnState(
        contentPadding = ScalingLazyColumnDefaults.padding(
            first = ScalingLazyColumnDefaults.ItemType.Text,
            last = ScalingLazyColumnDefaults.ItemType.MultiButton
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
                    Text(text = stringResource(R.string.app_name))
                }
            }


            items(PrayerOption.entries) { option ->
                Chip(
                    label = stringResource(option.getTitle()),
                    icon = {
                        Icon(painterResource(option.getIcon()), null)
                    },
                    largeIcon = true,
                    onClick = { onItemClick(option.name) }
                )
            }


            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterHorizontally
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Button(
                        R.drawable.baseline_settings_24,
                        stringResource(R.string.settings),
                        onClick = navigateToSettings
                    )
                    Button(
                        R.drawable.baseline_info_outline_24,
                        stringResource(R.string.info),
                        onClick = navigateToInfo
                    )
                }
            }
        }
    }
}

fun PrayerOption.getIcon() = when (this) {
    PrayerOption.BirkatHamazon -> R.drawable.baseline_fastfood_24
    PrayerOption.AsherYatzar -> R.drawable.baseline_family_restroom_24
    PrayerOption.TefilatHaderech -> R.drawable.baseline_drive_eta_24
}