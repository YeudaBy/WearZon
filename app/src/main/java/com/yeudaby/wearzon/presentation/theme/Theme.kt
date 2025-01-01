package com.yeudaby.wearzon.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.wear.compose.material.MaterialTheme
import com.yeudaby.wearzon.R

val frankRuhiLibre = FontFamily(
    Font(R.font.frank_ruhl_libre, FontWeight.Normal),
)

@Composable
fun WearzonTheme(
    content: @Composable () -> Unit
) {
    // todo
    MaterialTheme(
        content = content
    )
}