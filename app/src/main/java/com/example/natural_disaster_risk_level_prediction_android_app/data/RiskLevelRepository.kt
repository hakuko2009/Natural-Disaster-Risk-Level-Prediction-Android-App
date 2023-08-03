package com.example.natural_disaster_risk_level_prediction_android_app.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.natural_disaster_risk_level_prediction_android_app.api.RiskLevelApi
import com.example.natural_disaster_risk_level_prediction_android_app.model.WeatherAttribute
import com.example.natural_disaster_risk_level_prediction_android_app.model.RiskLevel
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Singleton
class RiskLevelRepository @Inject constructor(
    private val riskLevelApi: RiskLevelApi
) {
    val liveRiskLevel = MutableLiveData<RiskLevel>()
    fun getRiskLevel(data: WeatherAttribute) : MutableLiveData<RiskLevel>{
        riskLevelApi.getRiskLevel(
            temp = data.temp,
            pressure = data.pressure,
            humidity = data.humidity,
            clouds = data.clouds,
            windSpeed = data.windSpeed,
            windDeg = data.windDeg,
            weatherMain = data.weatherMain,
            weatherDes = data.weatherDesc,
            rain1h = data.rain
        ).enqueue(object : Callback<RiskLevel> {
            override fun onResponse(call: Call<RiskLevel>, response: Response<RiskLevel>) {
                if (response.isSuccessful) {
                    liveRiskLevel.value = response.body()

                } else {
                    Log.d("FAILED TAG 1", response.code().toString())
                }
            }

            override fun onFailure(call: Call<RiskLevel>, t: Throwable) {
                Log.d("FAILED TAG 2", t.message.toString())
            }
        })
        return liveRiskLevel
    }
}
