package com.ms.salawat.screen
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ms.salawat.R
import com.ms.salawat.data.model.AzkarAll


fun handleUserFeedback(context: Context, audioManager: AudioManager) {
    when (audioManager.ringerMode) {
        AudioManager.RINGER_MODE_NORMAL -> {
            // وضع عام: شغل الصوت واهتزاز خفيف (اختياري)
            vibrateDevice(context)
        }
        AudioManager.RINGER_MODE_VIBRATE -> {
            // وضع اهتزاز: اهتزاز فقط بدون صوت
            vibrateDevice(context)
        }
    }
}

fun playClickSound(context: Context) {
    try {
        val mediaPlayer = MediaPlayer.create(context, R.raw.sound)
        mediaPlayer?.let {
            it.setOnCompletionListener { mp -> mp.release() }
            it.start()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun vibrateDevice(context: Context) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(50)
    }
}

@Composable
fun AxkarItemAlSabah(azkarAll: AzkarAll, onAction: () -> Unit) {
    val count = rememberSaveable { mutableStateOf(azkarAll.count.toIntOrNull() ?: 0) }
    val isFinished = count.value == 0

    Card(
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (count.value > 0) {
                    count.value--
                    onAction()
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = azkarAll.dhikr,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                color = Color.Black
            )
            if (azkarAll.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = azkarAll.description,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive,
                    color = Color(0xFFB47B00)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(45.dp)
                    .border(
                        width = 2.dp,
                        color = if (isFinished) Color.Gray else Color(0xFF00695C),
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = "${count.value}",
                    fontSize = 16.sp,
                    color = if (isFinished) Color.Gray else Color(0xFF00695C)
                )
            }
        }
    }
}

fun loadAzkarAll(context: Context): List<AzkarAll> {
    return try {
        val jsonString = context.assets.open("Azkar/azkar.json")
            .bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<AzkarAll>>() {}.type
        Gson().fromJson(jsonString, type)
    } catch (e: Exception) {
        emptyList()
    }
}