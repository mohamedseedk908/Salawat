package com.ms.salawat.screen

import android.content.Context
import android.media.AudioManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AzkarCategoriesScreen(navController: NavHostController) {
    val context = LocalContext.current
    val allAzkar = remember { loadAzkarAll(context) }

    val categories = remember(allAzkar) {
        allAzkar.map { it.category }.distinct()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDFBFF))
    ) {
        CenterAlignedTopAppBar(
            title = { Text("أذكار متنوعة", fontWeight = FontWeight.Bold) }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { categoryName ->
                CategoryItemCard(
                    categoryName = categoryName,
                    onCategoryClick = {
                        // 🟢 مشفر الاسم عشان الحروف العربية والمسافات متبوظش الـ Route
                        val encodedCategory = Uri.encode(categoryName)
                        navController.navigate("AzkarDetailsScreen/$encodedCategory")
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryItemCard(categoryName: String, onCategoryClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCategoryClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00695C)
            )

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AzkarDetailsScreen(categoryName: String) {
    val context = LocalContext.current
    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }

    val allAzkar = remember { loadAzkarAll(context) }

    // 🟢 فلترة الأذكار حسب القسم الذي تم الضغط عليه
    val filteredAzkar = remember(allAzkar, categoryName) {
        allAzkar.filter { it.category == categoryName }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDFBFF))
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = categoryName, fontWeight = FontWeight.Bold) }
        )

        LazyColumn(
            contentPadding = PaddingValues(8.dp)
        ) {
            items(filteredAzkar) { azkar ->
                // 🟢 عرض كل ذكر داخل الـ Item الخاص به
                AxkarItemAlSabah(
                    azkarAll = azkar,
                    onAction = { handleUserFeedback(context, audioManager) }
                )
            }
        }
    }
}