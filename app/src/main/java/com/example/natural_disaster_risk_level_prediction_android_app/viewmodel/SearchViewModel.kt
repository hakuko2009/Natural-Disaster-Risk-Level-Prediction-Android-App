import androidx.lifecycle.ViewModel
import com.example.natural_disaster_risk_level_prediction_android_app.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    fun getCurrentWeather() = repository.getCurrentWeather()

    fun getForecastWeather() = repository.getForecastWeather()

    fun searchByName(city: String) = repository.searchByName(city)

    fun searchByGPS(lat: Double, lon: Double) = repository.searchByGPS(lat,lon)
}
