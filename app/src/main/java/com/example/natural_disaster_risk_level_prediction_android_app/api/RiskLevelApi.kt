package com.example.natural_disaster_risk_level_prediction_android_app.api

import retrofit2.http.Field
import retrofit2.http.GET

interface RiskLevelApi {
    companion object {
        const val BASE_URL = "https://disaster-risk-prediction-1.herokuapp.com/"
        const val PREDICT = "predict"
    }

    @GET(PREDICT)
    fun getRiskLevel(
        @Field("year") year: Int,
        @Field("type") type: String,
        @Field("region") region: String,
        @Field("magValue") magValue: Double,
        @Field("magScale") magScale: String,
        @Field("startMonth") startMonth: Int,
        @Field("endMonth") endMonth: Int
    ): Int
}
