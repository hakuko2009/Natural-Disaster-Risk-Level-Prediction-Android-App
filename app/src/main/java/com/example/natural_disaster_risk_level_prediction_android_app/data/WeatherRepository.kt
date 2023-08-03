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
        currentDatabase.currentDao().get()

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
        forecastDatabase.forecastDao().get()

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
}
