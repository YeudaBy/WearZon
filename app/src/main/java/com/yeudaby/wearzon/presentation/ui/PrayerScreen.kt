package com.yeudaby.wearzon.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Text
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.google.android.horologist.compose.pager.PageScreenIndicatorState
import com.yeudaby.wearzon.R
import com.yeudaby.wearzon.presentation.data.FontSize
import com.yeudaby.wearzon.presentation.data.NusachOption
import com.yeudaby.wearzon.presentation.data.PrayerOption
import com.yeudaby.wearzon.presentation.data.PreferencesKeys
import com.yeudaby.wearzon.presentation.data.loadPrayerTexts
import com.yeudaby.wearzon.presentation.data.readSetting
import com.yeudaby.wearzon.presentation.theme.frankRuhiLibre

@Composable
fun PrayerScreen(prayer: PrayerOption) {

    val context = LocalContext.current

    val nusach by readSetting(
        context,
        PreferencesKeys.NUSACH
    ).collectAsState(NusachOption.ASHKENAZ.name)

    var paragraphs by remember { mutableStateOf<List<String>?>(null) }

    LaunchedEffect(prayer, nusach) {
        val data = loadPrayerTexts(context)
        val prayerObj = when (prayer) {
            PrayerOption.TefilatHaderech -> data.tefilat_haderech
            PrayerOption.AsherYatzar -> data.asher_yatzar
            PrayerOption.BirkatHamazon -> data.birkat_hamazon
        }
        paragraphs = when (nusach) {
            NusachOption.SEFARD.name -> prayerObj.sefard
            NusachOption.EDOT_HAMIZRACH.name -> prayerObj.edot_hamizrach
            else -> prayerObj.ashkenaz
        }
    }

    Box {
        if (paragraphs == null) return@Box

        when (paragraphs!!.size > 1) {
            true -> Box {
                val pageState = rememberPagerState { paragraphs?.size ?: 1 }

                HorizontalPager(pageState, Modifier.fillMaxSize()) { page ->
                    Paragraph(paragraphs!![page], stringResource(prayer.getTitle()))
                }

                val pagerScreenState = remember { PageScreenIndicatorState(state = pageState) }
                HorizontalPageIndicator(pageIndicatorState = pagerScreenState)
            }

            false -> Box(Modifier.fillMaxSize()) {
                Paragraph(paragraphs!![0], stringResource(prayer.getTitle()))
            }
        }
    }
}

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun Paragraph(paragraph: String, title: String) {
    val context = LocalContext.current

    val nikud by readSetting(context, PreferencesKeys.NIKUD).collectAsState(true)
    val fontSize by readSetting(
        context,
        PreferencesKeys.FONT_SIZE
    ).collectAsState(FontSize.MEDIUM.name)

    val columnState = rememberResponsiveColumnState(
        contentPadding = ScalingLazyColumnDefaults.padding(
            first = ScalingLazyColumnDefaults.ItemType.Text,
            last = ScalingLazyColumnDefaults.ItemType.Text
        ),
    )

    val listState = rememberScalingLazyListState()

    ScreenScaffold(
        scrollState = columnState,
        positionIndicator = {
            PositionIndicator(scalingLazyListState = listState)
        }
    ) {
        ScalingLazyColumn(
            columnState = columnState, modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                ResponsiveListHeader(contentPadding = firstItemPadding()) {
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            val lines = paragraph.split("\n")
            items(lines) { line ->
                Text(
                    text = if (nikud == false) removeNikud(line) else line,
                    textAlign = TextAlign.Center,
                    fontFamily = frankRuhiLibre,
                    fontSize = FontSize.valueOf(fontSize ?: FontSize.MEDIUM.name).toTextUnit()
                )
            }
        }
    }
}

fun FontSize?.toTextUnit(): TextUnit = when (this) {
    FontSize.SMALL -> 13.sp
    FontSize.LARGE -> 17.sp
    else -> 15.sp
}


fun PrayerOption.getTitle(): Int {
    return when (this) {
        PrayerOption.TefilatHaderech -> R.string.tfilat_haderech
        PrayerOption.AsherYatzar -> R.string.hasher_yazar
        PrayerOption.BirkatHamazon -> R.string.bircat_hamazon
    }
}

fun removeNikud(text: String): String {
    return text.replace(Regex("[\u0591-\u05C7\u064B-\u0652\u0670\u0640]"), "")
}
