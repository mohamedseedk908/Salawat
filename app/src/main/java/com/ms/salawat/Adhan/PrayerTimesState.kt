package com.ms.salawat.Adhan
data class PrayerTimesState (
    val Fajr: String? = "--:--",
    val Sunrise: String? = "--:--",
    val Dhuhr: String? = "--:--",
    val Asr: String? = "--:--",
    val Maghrib: String? = "--:--",
    val Isha: String? = "--:--"
)
data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

// قائمة بالمدن الشهيرة (تقدر تزود عليها زي ما تحب)
val defaultCities = listOf(
    City("القاهرة", 30.0444, 31.2357),
    City("الإسكندرية", 31.2001, 29.9187),
    City("مكة المكرمة", 21.3891, 39.8579),
    City("المدينة المنورة", 24.5247, 39.5692),
    City("الرياض", 24.7136, 46.6753),
    City("دبي", 25.2048, 55.2708),
    City("عَمّان", 31.9454, 35.9284)
)