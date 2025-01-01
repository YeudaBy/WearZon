package com.yeudaby.wearzon.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.foundation.lazy.items
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.google.android.horologist.compose.material.SecondaryTitle
import com.google.android.horologist.compose.material.Title
import com.google.android.horologist.compose.material.ToggleChip
import com.google.android.horologist.compose.material.ToggleChipToggleControl
import com.yeudaby.wearzon.R
import com.yeudaby.wearzon.presentation.data.FontSize
import com.yeudaby.wearzon.presentation.data.NusachOption
import com.yeudaby.wearzon.presentation.data.PreferencesKeys
import com.yeudaby.wearzon.presentation.data.readSetting
import com.yeudaby.wearzon.presentation.data.saveSetting
import kotlinx.coroutines.launch

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val nikud by readSetting(context, PreferencesKeys.NIKUD).collectAsState(initial = true)

    val nusach by readSetting(
        context,
        PreferencesKeys.NUSACH
    ).collectAsState(initial = NusachOption.ASHKENAZ.name)

    val fontSize by readSetting(
        context,
        PreferencesKeys.FONT_SIZE
    ).collectAsState(initial = FontSize.MEDIUM.name)

    val nusachOptions = NusachOption.entries.toTypedArray()
    val fontSizeOptions = FontSize.entries.toTypedArray()

    val columnState = rememberResponsiveColumnState(
        contentPadding = ScalingLazyColumnDefaults.padding(
            first = ScalingLazyColumnDefaults.ItemType.Text,
            last = ScalingLazyColumnDefaults.ItemType.Chip
        )
    )

    ScreenScaffold(scrollState = columnState) {
        ScalingLazyColumn(
            columnState = columnState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                ResponsiveListHeader {
                    Title(stringResource(R.string.settings))
                }
            }

            item {
                ToggleChip(
                    checked = nikud ?: true,
                    onCheckedChanged = {
                        scope.launch {
                            saveSetting(context, PreferencesKeys.NIKUD, it)
                        }
                    },
                    label = stringResource(R.string.showNikud),
                    toggleControl = ToggleChipToggleControl.Switch
                )
            }

            item {
                SecondaryTitle(stringResource(R.string.choose_nusach))
            }

            items(nusachOptions) { option ->
                ToggleChip(
                    checked = nusach == option.name,
                    onCheckedChanged = {
                        scope.launch {
                            Log.d("SettingsScreen", "option: $option, nusach: $nusach")
                            saveSetting(context, PreferencesKeys.NUSACH, option.name)
                            Log.d("SettingsScreen", "option: $option, nusach: $nusach")
                        }
                    },
                    label = stringResource(option.displayName),
                    toggleControl = ToggleChipToggleControl.Radio
                )
            }

            item {
                SecondaryTitle(stringResource(R.string.choose_font_size))
            }

            items(fontSizeOptions) { option ->
                ToggleChip(
                    checked = fontSize == option.name,
                    onCheckedChanged = {
                        scope.launch {
                            saveSetting(context, PreferencesKeys.FONT_SIZE, option.name)
                        }
                    },
                    label = stringResource(option.displayName),
                    toggleControl = ToggleChipToggleControl.Radio
                )
            }
        }
    }
}