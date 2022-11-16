package com.example.natural_disaster_risk_level_prediction_android_app.data

import com.example.natural_disaster_risk_level_prediction_android_app.api.RiskLevelApi
import com.example.natural_disaster_risk_level_prediction_android_app.model.NaturalDisaster
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RiskLevelRepository @Inject constructor(
    private val riskLevelApi: RiskLevelApi
) {
    fun getRiskLevel(data: NaturalDisaster) {
        riskLevelApi.getRiskLevel(
            year = data.year,
            type = data.type.value,
            region = data.region.value,
            magValue = data.magValue,
            magScale = data.magScale.value,
            startMonth = data.startMonth,
            endMonth = data.endMonth
        )
    }
}
