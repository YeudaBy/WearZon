package com.yeudaby.wearzon.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme
import com.yeudaby.wearzon.R

val frankRuhiLibre = FontFamily(
    Font(R.font.frank_ruhl_libre, FontWeight.Normal),
)

@Composable
fun WearzonTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = Colors(
            primary = Color(0xFFDAE1FF),
            secondary = Color(0xff4d4d4d)
        ),
        content = content
    )
}