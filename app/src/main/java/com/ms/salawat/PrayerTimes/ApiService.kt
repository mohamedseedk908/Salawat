package com.ms.salawat.PrayerTimes

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("timingsByCity")
    suspend fun getPrayerTimes(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("method") method: Int
    ): Response<PrayerResponse>
}