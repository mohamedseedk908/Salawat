package com.ms.salawat.screen.al_massa
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.ms.salawat.screen.AxkarItemAlSabah
import com.ms.salawat.screen.handleUserFeedback
import com.ms.salawat.screen.loadAzkarAll

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ScreenAlMassa() {
    val context = LocalContext.current
    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    val allAzkar = remember { loadAzkarAll(context) }
    val sabahList = remember(allAzkar) {
        allAzkar.filter { it.category == "أذكار المساء" }
    }

    // 🟢 بدل الـ Scaffold بنستخدم Column عادي ممسوك من الشاشة بدون طبقات زائدة
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffEDFBFF))
    ) {
        // الـ TopAppBar ينزل في أعلى القائمة عادي
        CenterAlignedTopAppBar(
            title = {
                Text("أذكار المساء")
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