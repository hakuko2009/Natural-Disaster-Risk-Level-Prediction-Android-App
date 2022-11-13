package com.example.natural_disaster_risk_level_prediction_android_app.data

import androidx.lifecycle.liveData
import com.example.natural_disaster_risk_level_prediction_android_app.api.WeatherApi
import com.example.natural_disaster_risk_level_prediction_android_app.configuration.Resource
import com.example.natural_disaster_risk_level_prediction_android_app.configuration.Settings
import com.example.natural_disaster_risk_level_prediction_android_app.database.CurrentWeatherDatabase
import com.example.natural_disaster_risk_level_prediction_android_app.database.ForecastWeatherDatabase
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

@Singleton
class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val currentDatabase: CurrentWeatherDatabase,
    private val forecastDatabase: ForecastWeatherDatabase
) {

    fun getCurrentWeather() = liveData(Dispatchers.IO) {
        val db = currentDatabase.currentDao().get()

        if (db == null)
            emit(
                Resource.loadingDbNull(
                    data = currentDatabase.currentDao().get()
                )
            )
        else
            emit(
                Resource.loadingDbFull(
                    data = currentDatabase.currentDao().get()
                )
            )

        if (Settings.SHOULD_UPDATE) {
            try {
                currentDatabase.currentDao()
                    .insert(api.getCurrentByGPS())
                emit(
                    Resource.success(
                        currentDatabase.currentDao().get()
                    )
                )

            } catch (exception: Exception) {
                emit(Resource.error(data = null))
                exception.printStackTrace()
            }
            Settings.SHOULD_UPDATE = false
        }
    }

    fun getForecastWeather() = liveData(Dispatchers.IO) {
        val db = forecastDatabase.forecastDao().get()

        if (db == null)
            emit(
                Resource.loadingDbNull(
                    data = forecastDatabase.forecastDao().get()
                )
            )
        else
            emit(
                Resource.loadingDbFull(
                    forecastDatabase.forecastDao().get()
                )
            )

        if (Settings.SHOULD_UPDATE) {
            try {
                forecastDatabase.forecastDao()
                    .insert(api.getForecastByGPS())
                emit(
                    Resource.success(
                        forecastDatabase.forecastDao().get()
                    )
                )
            } catch (exception: Exception) {
                emit(Resource.error(data = null))
            }
        }
    }

    fun searchByName(city: String) = liveData(Dispatchers.IO) {
        emit(
            Resource.loading(
                data = null
            )
        )
        try {
            emit(Resource.success(api.searchByName(city = city)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null))
        }

    }

    fun searchByGPS(latitude: Double, longitudes: Double) = liveData(Dispatchers.IO) {
        emit(
            Resource.loading(
                data = null
            )
        )
        try {
            emit(
                Resource.success(
                    api.searchByGPS(lat = latitude, lon = longitudes)
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null))
        }
    }
}
