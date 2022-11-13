package com.example.natural_disaster_risk_level_prediction_android_app.configuration

import java.util.Locale

class Settings {
    companion object {
        var SHOULD_UPDATE = true
        var lat: Float = 51.5085f
        var lon: Float = -0.1257f
        var unit: Unit = Unit.Metric
        var audio: Boolean = true
        var lastUpdate: Long = 0
        var locale: Locale = Locale.US
    }
}
