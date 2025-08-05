package com.yeudaby.wearzon.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.yeudaby.wearzon.presentation.theme.WearzonTheme
import com.yeudaby.wearzon.presentation.ui.AboutScreen
import com.yeudaby.wearzon.presentation.ui.ListScreen
import com.yeudaby.wearzon.presentation.ui.PrayerScreen
import com.yeudaby.wearzon.presentation.ui.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                WearApp()
            }
        }
    }
}

@Preview
@Composable
fun WearApp() {
    val navController = rememberSwipeDismissableNavController()

    WearzonTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            SwipeDismissableNavHost(
                navController = navController, startDestination = "list"
            ) {
                composable("list") {
                    ListScreen({ navController.navigate("settings") },
                        { navController.navigate("info") }) { id ->
                        navController.navigate("prayer/$id")
                    }
                }
                composable("prayer/{id}") {
                    PrayerScreen(
                        prayerName = it.arguments?.getString("id")!!,
                        back = { navController.navigateUp() }
                    )
                }
                composable("settings") {
                    SettingsScreen()
                }
                composable("info") {
                    AboutScreen()
                }
            }
        }
    }
}
