package com.ms.salawat.PrayerTimes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PrayerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<Timings?>(null)
    val uiState: StateFlow <Timings?> = _uiState.asStateFlow()
    fun getPrayerTimes() {
        viewModelScope.launch {

            val response = RetrofitClient.apiService.getPrayerTimes(
                city = "Cairo",
                country = "Egypt",
                method = 5
            )

            if (response.isSuccessful) {
                val prayerResponse = response.body()
                _uiState.value = response.body()?.data?.timings
            }

        }
    }
}