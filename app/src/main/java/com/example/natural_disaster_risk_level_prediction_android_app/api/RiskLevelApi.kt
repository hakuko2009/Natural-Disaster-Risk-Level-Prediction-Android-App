package com.example.natural_disaster_risk_level_prediction_android_app.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface RiskLevelApi {
    companion object {
        const val BASE_URL = "https://disaster-risk-prediction-1.herokuapp.com/"
        const val PREDICT = "predict"
    }

    @GET(PREDICT)
    fun getRiskLevel(
        @Query("year") year: Int,
        @Query("type") type: String,
        @Query("region") region: String,
        @Query("magValue") magValue: Double,
        @Query("magScale") magScale: String,
        @Query("startMonth") startMonth: Int,
        @Query("endMonth") endMonth: Int
    ): Call<Int>
}
