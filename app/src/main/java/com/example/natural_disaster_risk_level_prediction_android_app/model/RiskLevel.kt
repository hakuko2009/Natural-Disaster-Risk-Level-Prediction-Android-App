package com.example.natural_disaster_risk_level_prediction_android_app.model

import com.google.gson.annotations.SerializedName

data class RiskLevel(
    @SerializedName("risk_level")
    val riskLevel: Int
)

enum class LevelClassifier(val value: Int) {
    LEVEL_0(0),
    LEVEL_1(1),
    LEVEL_2(2),
    LEVEL_3(3)
}


