package com.yeudaby.wearzon.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.yeudaby.wearzon.presentation.data.PrayerItem
import com.yeudaby.wearzon.presentation.data.loadPrayerTexts


@OptIn(ExperimentalHorologistApi::class)
@Composable
fun ListScreen(
    navigateToSettings: () -> Unit,
    navigateToInfo: () -> Unit,
    onItemClick: (id: String) -> Unit,
) {

    val context = LocalContext.current

    var items by remember {
        mutableStateOf<List<PrayerItem>>(emptyList())
    }

    LaunchedEffect(Unit) { items = loadPrayerTexts(context) }

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

            items(items) { option ->
                Chip(
                    label = option.hebrewName,
                    icon = {
                        Icon(painterResource(option.getIcon(context)), null)
                    },
                    largeIcon = true,
                    onClick = { onItemClick(option.hebrewName) }
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
                        .padding(top = 12.dp)
                ) {
                    Button(
                        R.drawable.settings,
                        stringResource(R.string.settings),
                        onClick = navigateToSettings
                    )
                    Button(
                        R.drawable.badge_info,
                        stringResource(R.string.about),
                        onClick = navigateToInfo
                    )
                }
            }
        }
    }
}
