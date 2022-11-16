package com.example.natural_disaster_risk_level_prediction_android_app.viewmodel

import com.example.natural_disaster_risk_level_prediction_android_app.data.RiskLevelRepository
import com.example.natural_disaster_risk_level_prediction_android_app.model.NaturalDisaster
import javax.inject.Inject

class RiskLevelViewModel @Inject constructor(
    private val riskLevelRepository: RiskLevelRepository,
    private val data: NaturalDisaster
) {
    fun getRiskLevel() = riskLevelRepository.getRiskLevel(data)
}
