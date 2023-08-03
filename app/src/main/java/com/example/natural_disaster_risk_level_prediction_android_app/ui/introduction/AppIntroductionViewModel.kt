package com.example.natural_disaster_risk_level_prediction_android_app.ui.introduction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppIntroductionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is an Android App on predicting natural disaster risk level, giving preventive measures and showing weather attribute\'s map"
    }
    val text: LiveData<String> = _text
}
