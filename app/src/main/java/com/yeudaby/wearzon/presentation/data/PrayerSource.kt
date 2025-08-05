package com.yeudaby.wearzon.presentation.data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStreamReader

@Serializable
data class PrayerItem(
    val ashkenaz: List<String>,
    val sefard: List<String>? = null,
    val edot_hamizrach: List<String>? = null,
    val hebrewName: String,
    val icon: String
) {
    fun getIcon(context: Context): Int {
        return context.resources.getIdentifier(icon, "drawable", context.packageName)
    }
}

fun loadPrayerTexts(context: Context): List<PrayerItem> {
    val json = context.assets.open("prayers.json").use {
        InputStreamReader(it).readText()
    }
    return Json.decodeFromString(json)
}