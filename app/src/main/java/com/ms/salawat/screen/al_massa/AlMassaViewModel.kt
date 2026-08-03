package com.ms.salawat.screen.al_massa
import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ms.salawat.data.model.AzkarCategory
import com.ms.salawat.data.model.Zekr

class AlMassaViewModel(): ViewModel() {
    fun loadAzkarFromJson(context: Context): List<Zekr> {
        return try {
            val jsonString = context.assets.open("Azkar Al-Massa/azkar.json")
                .bufferedReader().use { it.readText() }
            // بنقرأ الملف كأنه AzkarResponse
            val responseType = object : TypeToken<AzkarCategory>() {}.type
            val response: AzkarCategory = Gson().fromJson(jsonString, responseType)

            // بنرجع الـ List اللي جوه الـ content
            response.content
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

}