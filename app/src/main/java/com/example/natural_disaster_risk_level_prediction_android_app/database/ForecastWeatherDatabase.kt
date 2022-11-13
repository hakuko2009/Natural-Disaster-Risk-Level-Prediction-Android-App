package com.example.natural_disaster_risk_level_prediction_android_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.natural_disaster_risk_level_prediction_android_app.model.ConvertForecastList
import com.example.natural_disaster_risk_level_prediction_android_app.model.ConvertWeatherList
import com.example.natural_disaster_risk_level_prediction_android_app.model.ForecastWeather

@Database(
    entities = [ForecastWeather::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ConvertForecastList::class, ConvertWeatherList::class)
abstract class ForecastWeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastWeatherDao
}
