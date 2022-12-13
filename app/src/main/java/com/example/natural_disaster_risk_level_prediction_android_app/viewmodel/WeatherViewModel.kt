package com.example.natural_disaster_risk_level_prediction_android_app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.natural_disaster_risk_level_prediction_android_app.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepository
) : ViewModel() {

    fun getCurrentWeather() = weatherRepo.getCurrentWeather()

    fun getForecastWeather() = weatherRepo.getForecastWeather()

}
