package com.yeudaby.wearzon.presentation.data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStreamReader

@Serializable
data class PrayerItem(
    val ashkenaz: List<String>,
    val sefard: List<String>,
    val edot_hamizrach: List<String>,
)

@Serializable
data class PrayerText(
    val tefilat_haderech: PrayerItem,
    val birkat_hamazon: PrayerItem,
    val asher_yatzar: PrayerItem
)

fun loadPrayerTexts(context: Context): PrayerText {
    val json = context.assets.open("prayers.json").use {
        InputStreamReader(it).readText()
    }
    return Json.decodeFromString(json)
}

enum class PrayerOption {
    BirkatHamazon, AsherYatzar, TefilatHaderech
}
