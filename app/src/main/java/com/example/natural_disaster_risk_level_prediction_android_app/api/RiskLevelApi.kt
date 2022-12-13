package com.example.natural_disaster_risk_level_prediction_android_app.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RiskLevelApi {
    companion object {
        const val BASE_URL = "https://disaster-risk-prediction-1.herokuapp.com/"
        const val PREDICT = "predict"
    }

    @GET(PREDICT)
    fun getRiskLevel(
        @Query("temp") temp: Double,
        @Query("pressure") pressure: Double,
        @Query("humidity") humidity: Double,
        @Query("clouds") clouds: Double,
        @Query("wind_speed") windSpeed: Double,
        @Query("wind_deg") windDeg: Double,
        @Query("weather_main") weatherMain: String,
        @Query("weather_des") weatherDes: String,
        @Query("rain_1h") rain1h: Double
    ): Int
}
