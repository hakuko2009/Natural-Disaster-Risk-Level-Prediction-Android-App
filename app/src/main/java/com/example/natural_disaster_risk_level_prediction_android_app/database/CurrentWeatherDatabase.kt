package com.example.natural_disaster_risk_level_prediction_android_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.natural_disaster_risk_level_prediction_android_app.model.ConvertList
import com.example.natural_disaster_risk_level_prediction_android_app.model.CurrentWeather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Database(
    entities = [CurrentWeather::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(ConvertList::class)
abstract class CurrentWeatherDatabase : RoomDatabase() {
    abstract fun currentDao(): CurrentWeatherDao
}
