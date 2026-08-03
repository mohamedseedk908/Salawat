package com.ms.salawat.screen.al_prayer
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ms.salawat.data.model.AzkarAll
import com.ms.salawat.screen.AxkarItemAlSabah
import com.ms.salawat.screen.handleUserFeedback

@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun ScreenPrayer(vm:AlPrayerViewModel= viewModel()) {
    val context = LocalContext.current
    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    val uiState =  (vm.loadAzkarPrayer(context =context ))
    val azkarList = uiState.map { AzkarAll(category = "", count = it.repeat.toString(), description = it.bless, reference = "", dhikr = it.zekr) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("أذكار الصلاة")
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // مهم
                .background(Color(0xffEDFBFF))
        ) {
            LazyColumn {
                items(azkarList) { azkar ->
                    AxkarItemAlSabah(
                        azkarAll = azkar,
                        onAction = {
                            handleUserFeedback(context, audioManager)
                        }
                    )
                }
            }
        }
    }
}
