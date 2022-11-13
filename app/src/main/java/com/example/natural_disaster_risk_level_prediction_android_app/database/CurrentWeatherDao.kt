package com.example.natural_disaster_risk_level_prediction_android_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.natural_disaster_risk_level_prediction_android_app.model.CurrentWeather

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentWeather: CurrentWeather)

    @Query("SELECT * FROM current_weather WHERE i = 0")
    fun get(): CurrentWeather
}
