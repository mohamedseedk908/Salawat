package com.ms.salawat.NavGraph
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ms.salawat.screen.AliadieiaScreen
import com.ms.salawat.screen.AltasbihScreen
import com.ms.salawat.screen.AzkarCategoriesScreen
import com.ms.salawat.screen.AzkarDetailsScreen
import com.ms.salawat.screen.BottomNavigationBar.BottomNavigationBar
import com.ms.salawat.screen.PrayerTimes.PrayerTimes
import com.ms.salawat.screen.Settings.SettingsScreen
import com.ms.salawat.screen.al_massa.ScreenAlMassa
import com.ms.salawat.screen.al_prayer.ScreenPrayer
import com.ms.salawat.screen.al_sabah.ScreenAlSabah
import com.ms.salawatr.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarScreens = listOf("HomeScreen", "PrayerTimes", "SettingsScreen")
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarScreens) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "HomeScreen",
            modifier = Modifier.padding(innerPadding),
            // 🟢 1. حركات الدخول والخروج العادية (الانتقال للأمام)
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300))
            }
        ) {
            composable("HomeScreen") { HomeScreen(navController) }
            composable("ScreenPrayer") { ScreenPrayer() }
            composable("ScreenAlSabah") { ScreenAlSabah() }
            composable("ScreenAlMassa") { ScreenAlMassa() }
            composable("AltasbihScreen") { AltasbihScreen() }
            composable("AliadieiaScreen") { AliadieiaScreen() }
            composable("SettingsScreen") { SettingsScreen() }
            composable("PrayerTimes") { PrayerTimes() }
            composable(route = "AzkarCategoriesScreen"){AzkarCategoriesScreen(navController)}
            composable("AzkarDetailsScreen/{category}") { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("category") ?: ""
                AzkarDetailsScreen(categoryName = categoryName)
            }
        }
    }
}

