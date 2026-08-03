package com.ms.salawatr
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ms.salawat.screen.MainScreenContent.MainScreenContent


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    // 🟢 ننادي MainScreenContent مباشرة
    MainScreenContent(navController = navController)
}