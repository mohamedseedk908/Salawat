package com.ms.salawat.screen.BottomNavigationBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ms.salawat.screen.MainScreenContent.MyImage

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // 🟢 2. عمل انحناء (Kerve) للحواف (يمكنك تغيير 24.dp للحجم المناسب لك)
                .clip(RoundedCornerShape(35.dp))
                .background(Color(0xFFB9D8F1))
                .navigationBarsPadding()
                .height(50.dp)
                .padding(horizontal = 16.dp), // مسافة داخلية للمحتوى
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(16.dp))
            MyImage(
                imageResourceId = com.ms.salawat.R.drawable.settings,
                onClick = {
                    if (currentRoute != "SettingsScreen") {
                        navController.navigate("SettingsScreen"){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState=true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                })
            Spacer(Modifier.width(12.dp))
            MyImage(
                imageResourceId = com.ms.salawat.R.drawable.vector10,
                onClick = {
                    if (currentRoute != "PrayerTimes") {
                        navController.navigate("PrayerTimes"){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState=true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                })
            Spacer(Modifier.width(12.dp))
            MyImage(
                imageResourceId = com.ms.salawat.R.drawable.home,
                onClick = {
                    if (currentRoute != "HomeScreen") {
                        navController.navigate("HomeScreen"){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState=true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
