package com.example.natural_disaster_risk_level_prediction_android_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.natural_disaster_risk_level_prediction_android_app.model.ForecastWeather

@Dao
interface ForecastWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecastWeather: ForecastWeather)

    @Query("SELECT * FROM forecast_table WHERE i=0")
    fun get(): ForecastWeather
}
