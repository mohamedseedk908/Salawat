import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.Madhab
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import com.google.android.gms.location.LocationServices
import com.ms.salawat.Adhan.City
import com.ms.salawat.Adhan.PrayerTimesState
import com.ms.salawat.Adhan.defaultCities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PrayerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PrayerTimesState())
    val uiState: StateFlow<PrayerTimesState?> = _uiState.asStateFlow()

    // 🟢 اسم المدينة الحالية المحددة (عشان نعرضها في الـ UI)
    private val _selectedCity = MutableStateFlow<String>("القاهرة")
    val selectedCity: StateFlow<String> = _selectedCity.asStateFlow()

    // عند اختيار مدينة يدوياً من القائمة
    fun onCitySelected(city: City) {
        _selectedCity.value = city.name
        calculatePrayerTimes(city.latitude, city.longitude)
    }

    // جلب المواقيت بناءً على اللوكيشن أو الافتراضي
    @SuppressLint("MissingPermission")
    fun loadPrayerTimes(context: Context, hasPermission: Boolean) {
        if (hasPermission) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    _selectedCity.value = "موقعي الحالي"
                    calculatePrayerTimes(location.latitude, location.longitude)
                } else {
                    useDefaultCity()
                }
            }.addOnFailureListener {
                useDefaultCity()
            }
        } else {
            useDefaultCity()
        }
    }

    private fun useDefaultCity() {
        val defaultCity = defaultCities.first() // القاهرة
        _selectedCity.value = defaultCity.name
        calculatePrayerTimes(defaultCity.latitude, defaultCity.longitude)
    }

    private fun calculatePrayerTimes(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val coordinates = Coordinates(latitude, longitude)
                val dateComponents = DateComponents.from(Date())
                val params = CalculationMethod.EGYPTIAN.parameters
                params.madhab = Madhab.SHAFI

                val prayerTimes = PrayerTimes(coordinates, dateComponents, params)
                val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)

                _uiState.value = PrayerTimesState(
                    Fajr = formatter.format(prayerTimes.fajr),
                    Sunrise = formatter.format(prayerTimes.sunrise),
                    Dhuhr = formatter.format(prayerTimes.dhuhr),
                    Asr = formatter.format(prayerTimes.asr),
                    Maghrib = formatter.format(prayerTimes.maghrib),
                    Isha = formatter.format(prayerTimes.isha)
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}