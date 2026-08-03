import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ms.salawat.Adhan.defaultCities
import com.ms.salawat.PrayerTimes.PrayerItem
import com.ms.salawat.PrayerTimes.to12HourFormat

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PlayerTimes2(vm: PrayerViewModel = viewModel()) {
    val prayerTimes by vm.uiState.collectAsState()
    val currentCityName by vm.selectedCity.collectAsState()
    val context = LocalContext.current

    // حالة فتح/إغلاق القائمة المنسدلة
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }
    }
    LaunchedEffect(locationPermissionState.status.isGranted) {
        vm.loadPrayerTimes(
            context = context,
            hasPermission = locationPermissionState.status.isGranted
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🟢 1. زرار اختيار المدينة القائمة المنسدلة
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { isDropdownExpanded = true }
                    .padding(8.dp)
            ) {
                Text(
                    text = "المدينة: $currentCityName",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "اختيار المدينة"
                )
            }

            // القائمة المنسدلة للـ Cities
            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                defaultCities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(city.name) },
                        onClick = {
                            vm.onCitySelected(city) // تغيير المدينة
                            isDropdownExpanded = false // إغلاق القائمة
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🟢 2. الـ UI الخص بك لعرض المواقيت
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
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
}