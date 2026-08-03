package com.ms.salawat.screen
import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AltasbihScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("الرُّقية الشرعية من السنة النبوية")
                }
            )
        }
    ) { paddingValues ->
        val context = LocalContext.current
        val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
        val allAzkar = remember { loadAzkarAll(context) }
        val sabahList = remember(allAzkar) {
            allAzkar.filter { it.category == "الرُّقية الشرعية من السنة النبوية" }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // مهم
                .background(Color(0xffEDFBFF))
        ) {
            LazyColumn {
                items(sabahList) { azkar ->
                    AxkarItemAlSabah(azkarAll = azkar, onAction = { handleUserFeedback(context, audioManager) })
                }
            }
        }
    }

}


