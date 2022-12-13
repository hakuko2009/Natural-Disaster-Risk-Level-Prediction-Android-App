package com.example.natural_disaster_risk_level_prediction_android_app.data

import com.example.natural_disaster_risk_level_prediction_android_app.api.RiskLevelApi
import com.example.natural_disaster_risk_level_prediction_android_app.model.NaturalDisaster
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RiskLevelRepository @Inject constructor(
    private val riskLevelApi: RiskLevelApi
) {
    fun getRiskLevel(data: NaturalDisaster) = riskLevelApi.getRiskLevel(
        temp = data.temp,
        pressure = data.pressure,
        humidity = data.humidity,
        clouds = data.clouds,
        windSpeed = data.windSpeed,
        windDeg = data.windDeg,
        weatherMain = data.weatherMain,
        weatherDes = data.weatherDesc,
        rain1h = data.rain
    )
}
