package com.example.natural_disaster_risk_level_prediction_android_app.ui.weathermap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherMapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    private val _snackBar = MutableLiveData<String?>()

    val snackbar: LiveData<String?>
        get() = _snackBar

    val text: LiveData<String> = _text

    fun displayMessage(message: String) {
        _snackBar.value = message
    }

    fun onSnackbarShown() {
        _snackBar.value = null
    }
}
