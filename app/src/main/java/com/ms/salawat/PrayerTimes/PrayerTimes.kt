package com.ms.salawat.PrayerTimes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlayerTimes(vm: PrayerViewModel = viewModel()) {
    val prayerTimes by vm.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vm.getPrayerTimes()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween, // توزيع الصلاوات بمسافات متساوية
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrayerItem(title = "الفجر", time = prayerTimes?.Fajr.to12HourFormat())
        PrayerItem(title = "الشروق", time = prayerTimes?.Sunrise.to12HourFormat())
        PrayerItem(title = "الظهر", time = prayerTimes?.Dhuhr.to12HourFormat())
        PrayerItem(title = "العصر", time = prayerTimes?.Asr.to12HourFormat())
        PrayerItem(title = "المغرب", time = prayerTimes?.Maghrib.to12HourFormat())
        PrayerItem(title = "العشاء", time = prayerTimes?.Isha.to12HourFormat())
    }
}

// 🟢 Reusable Composable لعرض اسم الصلاة وتحته الوقت
@Composable
fun PrayerItem(
    title: String,
    time: String?,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // اسم الصلاة
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFF0C0C0A)
        )

        // فاصل راسي مناسب بين العنوان والتوقيت
        Spacer(modifier = Modifier.height(8.dp))

        // الوقت
        Text(
            text = time ?: "--:--",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            color = Color(0xFF050505)
        )
    }
}


// دالة تحول صيغة "19:30" إلى "7:30" أو "07:30"
@RequiresApi(Build.VERSION_CODES.O)
fun String?.to12HourFormat(): String {
    if (this.isNullOrBlank()) return "--:--"

    // بخلية يرجع ليا قيمة جوة ترو و كاتش
    return try {
        // بتشيل أي مسافات زيادت في الأول أو الآخر.
        val timeString = this.trim()

        // تحديد الصيغة حسب وجود ثواني أم لا
        val inputFormatter = if (timeString.split(":").size == 3) {
            DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH)
        } else {
            DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
        }

        val time = LocalTime.parse(timeString, inputFormatter)

        // تعديل التنسيق هنا: mm للدقائق (عشان لو 5 دقائق تطلع 05 مش 5)
        // h تعني ساعة بدون صفر على الشمال (مثال: 7:05)
        // hh تعني ساعة بصفر على الشمال (مثال: 07:05)
        val outputFormatter = DateTimeFormatter.ofPattern("h:mm", Locale.ENGLISH)

        time.format(outputFormatter)
    } catch (e: Exception) {
        this // لو حصلت مشكلة في النص المرجع يعرضه زي ما هو
    }
}