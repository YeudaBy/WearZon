package com.yeudaby.wearzon.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.RadioButton
import androidx.wear.compose.material.Switch
import androidx.wear.compose.material.Text
import com.yeudaby.wearzon.presentation.data.FontSize
import com.yeudaby.wearzon.presentation.data.NusachOption
import com.yeudaby.wearzon.presentation.data.PreferencesKeys
import com.yeudaby.wearzon.presentation.data.readSetting
import com.yeudaby.wearzon.presentation.data.saveSetting
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val nikud by readSetting(context, PreferencesKeys.NIKUD).collectAsState(initial = false)

    val nusach by readSetting(
        context,
        PreferencesKeys.NUSACH
    ).collectAsState(initial = NusachOption.ASHKENAZ)

    val fontSize by readSetting(
        context,
        PreferencesKeys.FONT_SIZE
    ).collectAsState(initial = FontSize.MEDIUM)

    val nusachOptions = NusachOption.entries.toTypedArray()

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                "הגדרות",
                style = MaterialTheme.typography.title2,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("ניקוד", style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = nikud ?: false,
                    onCheckedChange = {
                        scope.launch { saveSetting(context, PreferencesKeys.NIKUD, it) }
                    }
                )
            }
        }

        items(nusachOptions) { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        scope.launch {
                            saveSetting(
                                context,
                                PreferencesKeys.NUSACH,
                                option.toString()
                            )
                        }
                    }
            ) {
                RadioButton(
                    selected = nusach == option,
                    onClick = {
                        scope.launch {
                            saveSetting(
                                context,
                                PreferencesKeys.NUSACH,
                                option.toString()
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(option.toString(), style = MaterialTheme.typography.body1)
            }
        }

        item {
            Text(
                "גודל פונט",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}