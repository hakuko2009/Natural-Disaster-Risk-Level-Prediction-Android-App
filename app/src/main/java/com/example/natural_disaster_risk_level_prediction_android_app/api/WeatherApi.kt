package com.example.natural_disaster_risk_level_prediction_android_app.api

import com.example.natural_disaster_risk_level_prediction_android_app.configuration.Settings
import com.example.natural_disaster_risk_level_prediction_android_app.model.CurrentWeather
import com.example.natural_disaster_risk_level_prediction_android_app.model.ForecastWeather
import com.example.natural_disaster_risk_level_prediction_android_app.model.SearchCity
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val CURRENT = "data/2.5/weather"
        const val FORECAST = "data/2.5/forecast"
        const val SEARCH_NAME = "geo/1.0/direct"
        const val SEARCH_GPS = "geo/1.0/reverse"
        // personal use
        var API_KEY = "YOUR API KEY"
    }

    @GET(CURRENT)
    suspend fun getCurrentByGPS(
        @Query("lat") lat: Float = Settings.lat,
        @Query("lon") lon: Float = Settings.lon,
        @Query("lang") lang: String = Settings.locale.language,
        @Query("units") unit: String = Settings.unit.name,
        @Query("appid") apiKey: String = API_KEY
    ): CurrentWeather

    @GET(FORECAST)
    suspend fun getForecastByGPS(
        @Query("lat") lat: Float = Settings.lat,
        @Query("lon") lon: Float = Settings.lon,
        @Query("lang") lang: String = Settings.locale.language,
        @Query("units") unit: String = Settings.unit.name,
        @Query("appid") apiKey: String = API_KEY
    ): ForecastWeather

    @GET(SEARCH_NAME)
    suspend fun searchByName(
        @Query("limit") limit: Int = 5,
        @Query("q") city: String,
        @Query("appid") apiKey: String = API_KEY
    ): List<SearchCity>

    @GET(SEARCH_GPS)
    suspend fun searchByGPS(
        @Query("limit") limit: Int = 5,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = API_KEY
    ): List<SearchCity>
}
