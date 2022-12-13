package com.example.natural_disaster_risk_level_prediction_android_app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.natural_disaster_risk_level_prediction_android_app.data.RiskLevelRepository
import com.example.natural_disaster_risk_level_prediction_android_app.model.NaturalDisaster
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RiskLevelViewModel @Inject constructor(
    private val riskLevelRepository: RiskLevelRepository
) : ViewModel() {
    fun getRiskLevel(data: NaturalDisaster) = riskLevelRepository.getRiskLevel(data)
}
