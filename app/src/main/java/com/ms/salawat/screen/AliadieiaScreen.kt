package com.ms.salawat.screen
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AliadieiaScreen() {
    val context = LocalContext.current
    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    val allAzkar = remember { loadAzkarAll(context) }
    val sabahList = remember(allAzkar) { allAzkar.filter { it.category == "الأذكار بعد السلام من الصلاة" } }

    // 🟢 بدل الـ Scaffold بنستخدم Column عادي ممسوك من الشاشة بدون طبقات زائدة
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffEDFBFF))
    ) {
        // الـ TopAppBar ينزل في أعلى القائمة عادي
        CenterAlignedTopAppBar(
            title = {
                Text("الأذكار بعد السلام من الصلاة")
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(sabahList) { azkar ->
                AxkarItemAlSabah(
                    azkarAll = azkar,
                    onAction = { handleUserFeedback(context, audioManager) }
                )
            }
        }
    }
}