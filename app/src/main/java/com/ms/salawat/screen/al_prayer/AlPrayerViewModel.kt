package com.ms.salawat.screen.al_prayer

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ms.salawat.data.model.AzkarCategory
import com.ms.salawat.data.model.Zekr

class AlPrayerViewModel(): ViewModel() {
    fun loadAzkarPrayer(context: Context): List<Zekr> {
        return try {
            val prayrJson = context.assets.open("Post Prayer Azkar/azkar.json")
                .bufferedReader().use { it.readText() }
            val type  = object : TypeToken<AzkarCategory>(){}.type
            val prayeFromJsom : AzkarCategory = Gson().fromJson( prayrJson,type)
            prayeFromJsom.content
        }catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }
}