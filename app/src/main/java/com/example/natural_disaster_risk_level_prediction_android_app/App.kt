package com.example.natural_disaster_risk_level_prediction_android_app

import android.content.Context
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.example.natural_disaster_risk_level_prediction_android_app.api.RiskLevelApi
import com.example.natural_disaster_risk_level_prediction_android_app.api.WeatherApi
import com.example.natural_disaster_risk_level_prediction_android_app.database.CurrentWeatherDatabase
import com.example.natural_disaster_risk_level_prediction_android_app.database.ForecastWeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class App : MultiDexApplication() {

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

        @Singleton
        @Provides
        fun provideWeatherApi(): WeatherApi {
            return Retrofit.Builder()
                .baseUrl(WeatherApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }

        @Singleton
        @Provides
        fun provideRiskLevelApi(): RiskLevelApi {
            return Retrofit.Builder()
                .baseUrl(RiskLevelApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RiskLevelApi::class.java)
        }

        @Singleton
        @Provides
        fun provideCurrentDB(@ApplicationContext app: Context) =
            Room.databaseBuilder(
                app,
                CurrentWeatherDatabase::class.java,
                "CurrentDB.db"
            ).build()

        @Singleton
        @Provides
        fun provideCurrentDao(db: CurrentWeatherDatabase) =
            db.currentDao()

        @Singleton
        @Provides
        fun provideForecastDB(@ApplicationContext app: Context) =
            Room.databaseBuilder(
                app,
                ForecastWeatherDatabase::class.java,
                "ForecastDB.db"
            ).build()

        @Singleton
        @Provides
        fun provideForecastDao(db: ForecastWeatherDatabase) =
            db.forecastDao()

    }
}
